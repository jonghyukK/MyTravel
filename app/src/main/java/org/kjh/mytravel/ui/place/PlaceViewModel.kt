package org.kjh.mytravel.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.ApiResult
import com.example.domain.entity.Place
import com.example.domain.usecase.GetPlaceUseCase
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MyTravel
 * Class: PlaceViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

data class PlaceUiState(
    val placeItem: Place? = null
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
    val uiState : StateFlow<PlaceUiState> = _uiState

    init {
        viewModelScope.launch {
            getPlaceUseCase(initPlaceName)
                .collect { result ->
                    when (result) {
                        is ApiResult.Loading -> {}
                        is ApiResult.Success -> _uiState.update {
                            it.copy(
                                placeItem = result.data
                            )
                        }
                        is ApiResult.Error -> {
                            Logger.e(result.throwable.localizedMessage)
                        }
                    }
                }
        }
    }
}