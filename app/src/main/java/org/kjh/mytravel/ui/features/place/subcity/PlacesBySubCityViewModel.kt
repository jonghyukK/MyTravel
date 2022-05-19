package org.kjh.mytravel.ui.features.place.subcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetPlacesBySubCityNameUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.common.ErrorMsg

/**
 * MyTravel
 * Class: PlaceListByCityNameViewModel
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityViewModel @AssistedInject constructor(
    private val getPlacesBySubCityNameUseCase: GetPlacesBySubCityNameUseCase,
    @Assisted private val initSubCityName: String
): ViewModel() {

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

    private val _uiState: MutableStateFlow<UiState<List<Place>>> = MutableStateFlow(UiState.Loading)
    val uiState : StateFlow<UiState<List<Place>>> = _uiState

    init {
        getPlacesBySubCityName()
    }

    // API - get Places By SubCityName.
    private fun getPlacesBySubCityName() {
        viewModelScope.launch {
            getPlacesBySubCityNameUseCase(initSubCityName)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _uiState.value = UiState.Loading

                        is ApiResult.Success ->
                            _uiState.value = UiState.Success(apiResult.data.map { it.mapToPresenter() })

                        is ApiResult.Error ->
                            _uiState.value = UiState.Error(ErrorMsg.ERROR_PLACE_BY_CITYNAME_FAIL)
                    }
                }
        }
    }
}