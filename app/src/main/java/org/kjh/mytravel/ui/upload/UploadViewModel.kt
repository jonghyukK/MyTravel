package org.kjh.mytravel.ui.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kjh.mytravel.data.model.KakaoSearchPlaceModel
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.usecase.UploadUseCase
import java.io.File
import javax.inject.Inject

/**
 * MyTravel
 * Class: UploadViewModel
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

data class UploadUiState(
    val selectedItems: List<MediaStoreImage> = listOf(),
    val placeItem    : KakaoSearchPlaceModel? = null,
    val content      : String? = "",
    val isLoading    : Boolean = false,
    val uploadSuccess: Boolean = false
)

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val makeUploadUseCase: UploadUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(UploadUiState())
    val uiState : StateFlow<UploadUiState> = _uiState

    val content = MutableLiveData<String>()

    fun updateSelectedImages(items: List<MediaStoreImage>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(selectedItems = items)
            }
        }
    }

    fun updatePlaceItem(item: KakaoSearchPlaceModel) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(placeItem = item)
            }
        }
    }

    fun makeUploadPost() {
        viewModelScope.launch {
            // File
            val fileBody = uiState.value.selectedItems.map {
                val file = File(it.realPath)
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("file", file.name, requestFile)
            }

            // Place
            val placeInfo = uiState.value.placeItem

            // Content
            val content = content.value.toString()

            if (uiState.value.selectedItems.isNotEmpty() && placeInfo != null) {
                makeUploadUseCase(
                    file = fileBody,
                    content = content,
                    placeName = placeInfo.placeName,
                    placeAddress = placeInfo.addressName,
                    placeRoadAddress = placeInfo.roadAddressName,
                    x = placeInfo.x,
                    y = placeInfo.y
                ).collect { result ->
                    when (result) {
                        is Result.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is Result.Success -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                uploadSuccess = true
                            )
                        }

                        is Result.Error -> _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }
}