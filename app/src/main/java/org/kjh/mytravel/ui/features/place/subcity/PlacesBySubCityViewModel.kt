package org.kjh.mytravel.ui.features.place.subcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetPlacesBySubCityNameUseCase
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.GlobalErrorHandler
import org.kjh.mytravel.ui.common.UiState

/**
 * MyTravel
 * Class: PlaceListByCityNameViewModel
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

class PlacesBySubCityViewModel @AssistedInject constructor(
    private val getPlacesBySubCityNameUseCase: GetPlacesBySubCityNameUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    @Assisted private val initSubCityName: String
): ViewModel() {

    data class PlacesBySubCityUiState(
        val isLoading              : Boolean = false,
        val placeBySubCityItems    : List<Place> = listOf(),
        val isExpandedBehavior     : Boolean = false,
        val isInitializedCameraMove : Boolean = false
    )

    private val _uiState: MutableStateFlow<PlacesBySubCityUiState> = MutableStateFlow(PlacesBySubCityUiState())
    val uiState : StateFlow<PlacesBySubCityUiState> = _uiState

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
                            val result = apiResult.data.map { it.mapToPresenter() }

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    placeBySubCityItems = result
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

    fun updateMotionState(value: Boolean) {
        Logger.e("$value")
        _uiState.update {
            it.copy(
                isExpandedBehavior = value
            )
        }
    }

    val updateBottomSheetState = fun(isExpanded: Boolean) {
        _uiState.update {
            it.copy(
                isExpandedBehavior = isExpanded
            )
        }
    }

    fun initializedCameraMove() {
        _uiState.update {
            it.copy(isInitializedCameraMove = true)
        }
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