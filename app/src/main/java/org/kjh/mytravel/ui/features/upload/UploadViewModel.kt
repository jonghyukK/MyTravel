package org.kjh.mytravel.ui.features.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetLoginPreferenceUseCase
import org.kjh.domain.usecase.UploadPostUseCase
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.model.MediaStoreImage
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.common.UiState
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

    fun requestUploadPost() {
        val imgPathList = _uploadItemState.value.selectedItems.map { it.realPath }
        val placeInfo   = _uploadItemState.value.placeItem
        val content     = content.value.toString()

        if (imgPathList.isEmpty() || placeInfo == null || content.isBlank()) {
            _uploadState.value = UiState.Error(Throwable("필수 입력 정보를 확인해 주세요."))
            return
        }

        viewModelScope.launch {
            uploadPostUseCase(
                email       = getLoginPreferenceUseCase().email,
                file        = imgPathList,
                content     = content,
                x           = placeInfo.x,
                y           = placeInfo.y,
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
                        _uploadState.value = UiState.Error(Throwable("occur Error [Upload API]"))
                }
            }
        }
    }
}