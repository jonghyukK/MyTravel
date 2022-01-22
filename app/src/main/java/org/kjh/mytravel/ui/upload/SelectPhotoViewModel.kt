package org.kjh.mytravel.ui.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.usecase.GetLocalImageUseCase
import org.kjh.mytravel.domain.usecase.UploadUseCase
import java.io.File
import javax.inject.Inject

/**
 * MyTravel
 * Class: SelectPhotoViewModel
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

data class SelectPhotoUiState(
    val mediaStoreImages : List<MediaStoreImage> = listOf(),
    val selectedItems    : List<MediaStoreImage> = listOf(),
    val isUploadLoading  : Boolean = false,
    val isUploadSuccess  : Boolean = false
)

@HiltViewModel
class SelectPhotoViewModel @Inject constructor(
    private val makeUploadUseCase: UploadUseCase,
    private val getLocalImageUseCase: GetLocalImageUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(SelectPhotoUiState())
    val uiState : StateFlow<SelectPhotoUiState> = _uiState

    val content = MutableLiveData<String>()

    init {
        loadImagesFromMediaStore()
    }

    private fun loadImagesFromMediaStore() {
        viewModelScope.launch {
            _uiState.value = SelectPhotoUiState(
                mediaStoreImages = getLocalImageUseCase()
            )
        }
    }

    fun addSelectedImages(items: List<MediaStoreImage>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(selectedItems = items)
            }
        }
    }

    fun uploadPost() {
        viewModelScope.launch {
            val fileBody = uiState.value.selectedItems.map {
                val file = File(it.realPath)
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("file", file.name, requestFile)
            }

            makeUploadUseCase(
                file         = fileBody,
                content      = content.value.toString(),
                cityName     = "서울",
                placeName    = "잠실타워",
                placeAddress = "서울시 성동구 잠실동 339-12"
            ).collect { result ->

                when (result) {

                    is Result.Loading -> _uiState.update {
                        it.copy(isUploadLoading = true)
                    }

                    is Result.Success ->
                        _uiState.update {
                            it.copy(isUploadSuccess = true, isUploadLoading = false)
                        }

                    is Result.Error -> {}
                }
            }
        }
    }
}