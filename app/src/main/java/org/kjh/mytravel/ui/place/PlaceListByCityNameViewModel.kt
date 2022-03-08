package org.kjh.mytravel.ui.place

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetPlacesBySubCityNameUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.mapToPresenter

/**
 * MyTravel
 * Class: PlaceListByCityNameViewModel
 * Created by jonghyukkang on 2022/03/08.
 *
 * Description:
 */

data class PlaceListCityNameUiState(
    val placeItems: List<Place> = listOf()
)

class PlaceListByCityNameViewModel @AssistedInject constructor(
    private val getPlacesBySubCityNameUseCase: GetPlacesBySubCityNameUseCase,
    @Assisted private val initSubCityName: String
): ViewModel() {

    @AssistedFactory
    interface SubCityNameAssistedFactory {
        fun create(subCityName: String): PlaceListByCityNameViewModel
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

    private val _uiState = MutableStateFlow(PlaceListCityNameUiState())
    val uiState : StateFlow<PlaceListCityNameUiState> = _uiState

    init {
        viewModelScope.launch {
            getPlacesBySubCityNameUseCase(initSubCityName)
                .collect { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            _uiState.update { currentUiState ->
                                currentUiState.copy(
                                    placeItems = result.data.map { it.mapToPresenter() }
                                )
                            }
                        }
                    }
                }
        }
    }
}