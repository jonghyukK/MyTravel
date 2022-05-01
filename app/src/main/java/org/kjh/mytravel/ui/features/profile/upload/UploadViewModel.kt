package org.kjh.mytravel.ui.features.profile.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetLoginPreferenceUseCase
import org.kjh.domain.usecase.UploadPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.*
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.common.ErrorMsg
import javax.inject.Inject

/**
 * MyTravel
 * Class: UploadViewModel
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

data class UploadItemState(
    val selectedItems: List<MediaStoreImage> = listOf(),
    val placeItem    : MapQueryItem? = null,
    val content      : String? = "",
)

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadPostUseCase        : UploadPostUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {
    private val _uploadItemState = MutableStateFlow(UploadItemState())
    val uploadItemState = _uploadItemState.asStateFlow()

    private val _uploadState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Init)
    val uploadState = _uploadState.asStateFlow()

    val content = MutableLiveData<String>()

    fun updateSelectedImages(items: List<MediaStoreImage>) {
        _uploadItemState.update {
            it.copy(selectedItems = items)
        }
    }

    fun updatePlaceItem(item: MapQueryItem) {
        _uploadItemState.update {
            it.copy(placeItem = item)
        }
    }

    fun initUploadState() {
        _uploadState.value = UiState.Init
    }

    // API - Upload Post.
    fun makeUploadPost() {
        viewModelScope.launch {
            val imgPathList = _uploadItemState.value.selectedItems.map { it.realPath }
            val placeInfo   = _uploadItemState.value.placeItem
            val content     = content.value.toString()

            if (imgPathList.isEmpty() || placeInfo == null || content.isBlank()) {
                _uploadState.value = UiState.Error(ErrorMsg.ERROR_UPLOAD_INPUT_CHECK)
                return@launch
            }

            uploadPostUseCase(
                email       = getLoginPreferenceUseCase().email,
                x           = placeInfo.x,
                y           = placeInfo.y,
                file        = imgPathList,
                content     = content,
                placeName   = placeInfo.placeName,
                placeAddress     = placeInfo.placeAddress,
                placeRoadAddress = placeInfo.placeRoadAddress
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _uploadState.value = UiState.Loading

                    is ApiResult.Success ->
                        _uploadState.value = UiState.Success(apiResult.data.mapToPresenter())

                    is ApiResult.Error ->
                        _uploadState.value = UiState.Error(ErrorMsg.ERROR_UPLOAD_POST_FAIL)
                }
            }
        }
    }
}