package org.kjh.mytravel.ui.profile.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.ApiResult
import com.example.domain.entity.MapSearch
import com.example.domain.usecase.SearchMapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    val placeList: List<MapSearch> = listOf()
)

@HiltViewModel
class MapSearchViewModel @Inject constructor(
    private val searchMapUseCase: SearchMapUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(MapSearchUiState())
    val uiState : StateFlow<MapSearchUiState> = _uiState

    val searchQuery = MutableLiveData<String>()

    fun makeSearchPlace() {
        viewModelScope.launch {
            searchMapUseCase(searchQuery.value.toString())
                .collect { result ->
                    when (result) {
                        is ApiResult.Loading -> _uiState.value =
                            MapSearchUiState(isLoading = true)

                        is ApiResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    placeList = result.data
                                )
                            }
                        }
                        is ApiResult.Error -> {}
                    }
                }
        }
    }
}