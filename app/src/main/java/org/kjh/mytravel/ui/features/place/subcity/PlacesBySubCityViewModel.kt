package org.kjh.mytravel.ui.features.place.subcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.Marker
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetPlacesBySubCityNameUseCase
import org.kjh.mytravel.model.*
import org.kjh.mytravel.ui.GlobalErrorHandler

/**
 * MyTravel
 * Class: PlaceListByCityNameViewModel
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */


data class PlacesBySubCityUiState(
    val isLoading : Boolean = false,
    val placeItems : List<Place> = listOf(),
    val placeWithMarkerMap : Map<String, PlaceWithMarker> = mapOf()
)

class PlacesBySubCityViewModel @AssistedInject constructor(
    private val getPlacesBySubCityNameUseCase: GetPlacesBySubCityNameUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    @Assisted private val initSubCityName: String
): ViewModel() {

    private val _uiState: MutableStateFlow<PlacesBySubCityUiState> = MutableStateFlow(PlacesBySubCityUiState())
    val uiState = _uiState.asStateFlow()

    private val _selectedPlaceMap: MutableStateFlow<PlaceWithMarker?> = MutableStateFlow(null)
    val selectedPlaceMap = _selectedPlaceMap.asStateFlow()

    private val _cameraMoveEvent: MutableStateFlow<CameraUpdate?> = MutableStateFlow(null)
    val cameraMoveEvent = _cameraMoveEvent.asStateFlow()

    private val _bottomSheetState = MutableStateFlow(STATE_COLLAPSED)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private var currentPlaceKey = ""

    init {
        fetchPlacesBySubCity()
    }

    private fun fetchPlacesBySubCity() {
        viewModelScope.launch {
            getPlacesBySubCityNameUseCase(initSubCityName)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }

                        is ApiResult.Success -> {
                            val placeItems = apiResult.data.map { it.mapToPresenter() }

                            _cameraMoveEvent.value = placeItems.cameraCenterInPlaces()

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    placeItems = placeItems,
                                    placeWithMarkerMap = placeItems.withMarkerToMap(::updateSelectedPlaceMap)
                                )
                            }
                        }

                        is ApiResult.Error -> {
                            Logger.e(apiResult.throwable.localizedMessage!!)
                            val errorMsg = "occur Error [Fetch Places by Subcity API]"
                            globalErrorHandler.sendError(errorMsg)

                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                        }
                    }
                }
        }
    }

    fun updateSelectedPlaceMap(marker: Marker?) {
        val mapItem = _uiState.value.placeWithMarkerMap

        if (currentPlaceKey.isNotEmpty() && currentPlaceKey != marker?.captionText ) {
            clearPrevSelectedMarker()
        }

        currentPlaceKey = marker?.captionText ?: ""
        _selectedPlaceMap.value = marker?.let {
            mapItem[currentPlaceKey]
        }
        _cameraMoveEvent.value = marker?.let {
            mapItem[currentPlaceKey]!!.placeItem.cameraMove()
        }
    }

    private fun clearPrevSelectedMarker() {
        val clearedMarker = _uiState.value.placeWithMarkerMap.toMutableMap().apply {
            this[currentPlaceKey] = this[currentPlaceKey]!!.copy(
                placeMarker = NaverMapUtils.getMarkerWithUnSelected(this[currentPlaceKey]!!.placeMarker))
        }

        _uiState.update {
            it.copy(placeWithMarkerMap = clearedMarker)
        }
    }

    val updateBottomSheetState = fun(state: Int) {
        _bottomSheetState.value = state
    }

    fun removeCameraEvent() {
        _cameraMoveEvent.value = null
    }

    @AssistedFactory
    interface SubCityNameAssistedFactory {
        fun create(subCityName: String): PlacesBySubCityViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: SubCityNameAssistedFactory,
            subCityName: String
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                assistedFactory.create(subCityName) as T
        }
    }
}