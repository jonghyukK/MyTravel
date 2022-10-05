package org.kjh.mytravel.ui.features.place.infowithdaylog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetMyProfileUseCase
import org.kjh.domain.usecase.GetPlaceUseCase
import org.kjh.mytravel.model.*
import org.kjh.mytravel.utils.containPlace

/**
 * MyTravel
 * Class: PlaceViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

class PlaceInfoWithDayLogViewModel @AssistedInject constructor(
    private val getPlaceUseCase : GetPlaceUseCase,
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val eventHandler    : EventHandler,
    @Assisted private val initPlaceName: String
): ViewModel() {

    private val _uiState = MutableStateFlow(PlaceUiState())
    val uiState = _uiState.asStateFlow()

    val updateCollapsedState = fun(isCollapsed: Boolean) {
        _uiState.update {
            it.copy(isAppBarCollapsed = isCollapsed)
        }
    }

    init {
        fetchPlaceByPlaceName()
        updateBookmarkState()
    }

    private fun fetchPlaceByPlaceName() {
        viewModelScope.launch {
            getPlaceUseCase(initPlaceName)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }

                        is ApiResult.Success -> {
                            val placeItem = apiResult.data.mapToPresenter()

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    placeItem = placeItem,
                                    markerItem = placeItem.withMarker(),
                                    cameraMoveEvent = placeItem.cameraMove(CameraAnimation.None)
                                )
                            }
                        }

                        is ApiResult.Error -> {
                            Logger.e(apiResult.throwable.localizedMessage!!)

                            eventHandler.emitEvent(
                                Event.ApiError("occur error [Fetch PlaceDetail API]"))

                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                        }
                    }
                }
        }
    }

    private fun updateBookmarkState() {
        viewModelScope.launch {
            getMyProfileUseCase()
                .map { userEntity ->
                    userEntity?.bookMarks?.map { it.mapToPresenter() } ?: emptyList()
                }
                .distinctUntilChanged()
                .collect { bookmarks ->
                    _uiState.update {
                        it.copy(isBookmarked = bookmarks.containPlace(initPlaceName))
                    }
                }
        }
    }

    fun requestBookmarkUpdate() {
        viewModelScope.launch {
            eventHandler.emitEvent(Event.RequestUpdateBookmark(initPlaceName))
        }
    }

    fun clearMarkerAndCameraMoveEvent() {
        val mapClearedMarker = _uiState.value.markerItem?.let {
            it.copy(placeMarker = it.placeMarker.apply { map = null })
        }

        _uiState.update {
            it.copy(
                markerItem      = mapClearedMarker,
                cameraMoveEvent = null
            )
        }
    }

    @AssistedFactory
    interface PlaceNameAssistedFactory {
        fun create(placeName: String): PlaceInfoWithDayLogViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: PlaceNameAssistedFactory,
            placeName: String
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(placeName) as T
            }
        }
    }
}

data class PlaceUiState(
    val isLoading        : Boolean = false,
    val placeItem        : Place? = null,
    val isBookmarked     : Boolean = false,
    val isAppBarCollapsed: Boolean = false,
    val markerItem       : PlaceWithMarker? = null,
    val cameraMoveEvent  : CameraUpdate? = null
)