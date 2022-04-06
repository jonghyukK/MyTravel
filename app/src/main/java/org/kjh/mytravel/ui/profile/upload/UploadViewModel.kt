package org.kjh.mytravel.ui.profile.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.UploadPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

data class UploadItemState(
    val selectedItems: List<MediaStoreImage> = listOf(),
    val placeItem    : MapQueryItem? = null,
    val content      : String? = "",
)

sealed class UploadState {
    object Init : UploadState()
    object Loading: UploadState()
    data class Success(val userItem: User): UploadState()
    data class Error(val error: String?): UploadState()
}

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadPostUseCase: UploadPostUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {
    private val _uploadItemState = MutableStateFlow(UploadItemState())
    val uploadItemState = _uploadItemState.asStateFlow()

    private val _uploadState: MutableStateFlow<UploadState> = MutableStateFlow(UploadState.Init)
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
        _uploadState.value = UploadState.Init
    }

    fun makeUploadPost() {
        viewModelScope.launch {
            val imgPathList = _uploadItemState.value.selectedItems.map { it.realPath }
            val placeInfo   = _uploadItemState.value.placeItem
            val content     = content.value.toString()

            if (imgPathList.isEmpty() || placeInfo == null || content.isBlank()) {
                _uploadState.value = UploadState.Error("필수 입력 정보를 확인해 주세요.")
                return@launch
            }

            uploadPostUseCase(
                email = getLoginPreferenceUseCase().email,
                x = placeInfo.x,
                y = placeInfo.y,
                file = imgPathList,
                content = content,
                placeName = placeInfo.placeName,
                placeAddress = placeInfo.placeAddress,
                placeRoadAddress = placeInfo.placeRoadAddress
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _uploadState.value = UploadState.Loading

                    is ApiResult.Success ->
                        _uploadState.value = UploadState.Success(apiResult.data.mapToPresenter())

                    is ApiResult.Error ->
                        _uploadState.value = UploadState.Error(apiResult.throwable.localizedMessage)
                }
            }
        }
    }
}