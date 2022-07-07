package org.kjh.mytravel.ui.features.place.infowithdaylog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetPlaceUseCase
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.GlobalErrorHandler

/**
 * MyTravel
 * Class: PlaceViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

class PlaceInfoWithDayLogViewModel @AssistedInject constructor(
    private val getPlaceUseCase   : GetPlaceUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    @Assisted private val initPlaceName: String
): ViewModel() {

    data class PlaceUiState(
        val isLoading        : Boolean = false,
        val placeItem        : Place? = null,
        val isBookmarked     : Boolean = false,
        val isAppBarCollapsed: Boolean = false
    )

    private val _uiState = MutableStateFlow(PlaceUiState())
    val uiState = _uiState.asStateFlow()

    val updateCollapsedState = fun(isCollapsed: Boolean) {
        _uiState.update {
            it.copy(isAppBarCollapsed = isCollapsed)
        }
    }

    init {
        fetchPlaceDetailByPlaceName()
    }

    private fun fetchPlaceDetailByPlaceName() {
        viewModelScope.launch {
            getPlaceUseCase(initPlaceName)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }

                        is ApiResult.Success -> {
                            val result = apiResult.data.mapToPresenter()

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    placeItem = result
                                )
                            }
                        }

                        is ApiResult.Error -> {
                            Logger.e(apiResult.throwable.localizedMessage!!)

                            val errorMsg = "occur error [Fetch PlaceDetail API]"
                            globalErrorHandler.sendError(errorMsg)

                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                        }
                    }
                }
        }
    }

    fun updateBookmarkState(isBookmarked: Boolean) = _uiState.update {
        it.copy(isBookmarked = isBookmarked)
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