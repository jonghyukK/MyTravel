package org.kjh.mytravel.ui.features.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.UploadDayLogUseCase
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.model.MediaStoreImage
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.model.common.UiState
import javax.inject.Inject

/**
 * MyTravel
 * Class: UploadViewModel
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadDayLogUseCase : UploadDayLogUseCase
): ViewModel() {
    private val _uploadItem = MutableStateFlow(UploadItem())
    val uploadItem = _uploadItem.asStateFlow()

    private val _uploadState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Init)
    val uploadState = _uploadState.asStateFlow()

    fun requestUploadDayLog() {
        val imgPathList = _uploadItem.value.selectedImageItems.map { it.realPath }
        val placeInfo   = _uploadItem.value.placeItem!!
        val content     = _uploadItem.value.content

        viewModelScope.launch {
            uploadDayLogUseCase(
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
                        _uploadState.value = UiState.Error("occur Error [ Upload API ]")
                }
            }
        }
    }

    fun updateSelectedImages(items: List<MediaStoreImage>) {
        _uploadItem.update {
            it.copy(selectedImageItems = items)
        }
    }

    fun updatePlaceItem(item: MapQueryItem?) {
        _uploadItem.update {
            it.copy(placeItem = item)
        }
    }

    fun updateContent(content: String) {
        _uploadItem.update {
            it.copy(content = content)
        }
    }

    fun clearUploadItem() {
        _uploadItem.value = UploadItem()
    }

    fun initUploadState() {
        _uploadState.value = UiState.Init
    }

    fun isReadyUploadData() = _uploadItem.value.isFulfillEachItems()
}

data class UploadItem(
    val selectedImageItems: List<MediaStoreImage> = listOf(),
    val placeItem    : MapQueryItem? = null,
    val content      : String = "",
)

fun UploadItem.isFulfillEachItems(): Boolean =
    selectedImageItems.isNotEmpty() && placeItem != null && content.isNotBlank()