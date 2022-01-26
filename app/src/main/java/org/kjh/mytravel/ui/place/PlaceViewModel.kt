package org.kjh.mytravel.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.mytravel.data.model.Post
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.usecase.GetPlaceListUseCase
import javax.inject.Inject

/**
 * MyTravel
 * Class: PlaceViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

data class PlaceUiState(
    val placeName: String = "",
    val placeAddress: String = "",
    val placeRoadAddress: String = "",
    val cityName: String = "",
    val x: String = "",
    val y: String = "",
    val posts : List<Post> = listOf()
)

class PlaceViewModel @AssistedInject constructor(
    private val getPlaceListUseCase: GetPlaceListUseCase,
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
            getPlaceListUseCase.execute(initPlaceName)
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {}
                        is Result.Success -> _uiState.update {
                            it.copy(
                                placeName = result.data.data.placeName,
                                placeAddress = result.data.data.placeAddress,
                                placeRoadAddress = result.data.data.placeRoadAddress,
                                cityName = result.data.data.cityName,
                                x = result.data.data.x,
                                y = result.data.data.y,
                                posts = result.data.data.posts
                            )
                        }
                        is Result.Error -> {
                            Logger.e(result.throwable.localizedMessage)
                        }
                    }
                }
        }
    }
}