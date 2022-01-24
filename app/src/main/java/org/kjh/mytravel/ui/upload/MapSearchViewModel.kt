package org.kjh.mytravel.ui.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.mytravel.data.model.KakaoSearchPlaceModel
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.MapRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: MapSearchViewModel
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

data class MapSearchUiState(
    val isLoading: Boolean = false,
    val placeList: List<KakaoSearchPlaceModel> = listOf()
)

@HiltViewModel
class MapSearchViewModel @Inject constructor(
    private val mapRepository: MapRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MapSearchUiState())
    val uiState : StateFlow<MapSearchUiState> = _uiState

    val searchQuery = MutableLiveData<String>()

    fun makeSearchPlace() {
        viewModelScope.launch {
            mapRepository.searchPlace(searchQuery.value.toString())
                .collect { result ->

                    when (result) {
                        is Result.Loading -> _uiState.value =
                            MapSearchUiState(isLoading = true)

                        is Result.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    placeList = result.data.placeList
                                )
                            }
                        }
                        is Result.Error -> {}
                    }
                }
        }
    }
}