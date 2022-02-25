package org.kjh.mytravel.ui.profile.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.UploadPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.model.MediaStoreImage
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
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
    val placeItem    : MapQueryItem? = null,
    val content      : String? = "",
    val isLoading    : Boolean = false,
    val uploadSuccess: Boolean = false,
    val userItem     : User? = null
)

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadPostUseCase: UploadPostUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase
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

    fun updatePlaceItem(item: MapQueryItem) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(placeItem = item)
            }
        }
    }

    fun makeUploadPost() {
        viewModelScope.launch {
            val imgPathList = uiState.value.selectedItems.map {
                it.realPath
            }
            val placeInfo = uiState.value.placeItem
            val content = content.value.toString()

            if (uiState.value.selectedItems.isNotEmpty() && placeInfo != null) {
                uploadPostUseCase(
                    email            = getLoginPreferenceUseCase().email,
                    x                = placeInfo.x,
                    y                = placeInfo.y,
                    file             = imgPathList,
                    content          = content,
                    placeName        = placeInfo.placeName,
                    placeAddress     = placeInfo.placeAddress,
                    placeRoadAddress = placeInfo.placeRoadAddress
                ).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is ApiResult.Success -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                uploadSuccess = true,
                                userItem = result.data.mapToPresenter()
                            )
                        }

                        is ApiResult.Error -> _uiState.update { it.copy(isLoading = false) }
                    }
                }
            }
        }
    }
}