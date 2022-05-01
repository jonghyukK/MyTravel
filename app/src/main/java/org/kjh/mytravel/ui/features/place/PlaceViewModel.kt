package org.kjh.mytravel.ui.features.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetPlaceUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.mapToPresenter

/**
 * MyTravel
 * Class: PlaceViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

data class PlaceUiState(
    val isLoading: Boolean = false,
    val placeItem: Place? = null,
    val isError  : String? = null
)

class PlaceViewModel @AssistedInject constructor(
    private val getPlaceUseCase: GetPlaceUseCase,
    @Assisted private val initPlaceName: String
): ViewModel() {

    @AssistedFactory
    interface PlaceNameAssistedFactory {
        fun create(placeName: String): PlaceViewModel
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

    private val _uiState = MutableStateFlow(PlaceUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPlaceInfoWithPlaceName()
    }

    // API - get Place Info by placeName.
    private fun getPlaceInfoWithPlaceName() {
        viewModelScope.launch {
            getPlaceUseCase(initPlaceName)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _uiState.update {
                                it.copy(isLoading = true)
                            }

                        is ApiResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    placeItem = apiResult.data.mapToPresenter()
                                )
                            }
                        }

                        is ApiResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isError   = apiResult.throwable.localizedMessage
                                )
                            }
                        }
                    }
                }
        }
    }
}