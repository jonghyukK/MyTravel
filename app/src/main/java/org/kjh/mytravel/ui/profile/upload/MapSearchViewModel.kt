package org.kjh.mytravel.ui.profile.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.SearchMapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.model.mapToPresenter
import javax.inject.Inject

/**
 * MyTravel
 * Class: MapSearchViewModel
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

sealed class MapSearchState {
    object Init: MapSearchState()
    object Loading: MapSearchState()
    data class Success(val items: List<MapQueryItem>): MapSearchState()
    data class Error(val error: String?): MapSearchState()
}

@HiltViewModel
class MapSearchViewModel @Inject constructor(
    private val searchMapUseCase: SearchMapUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<MapSearchState> = MutableStateFlow(MapSearchState.Init)
    val uiState = _uiState.asStateFlow()

    val searchQuery = MutableLiveData<String>()

    fun makeSearchPlace() {
        viewModelScope.launch {
            searchMapUseCase(searchQuery.value.toString())
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _uiState.value = MapSearchState.Loading

                        is ApiResult.Success ->
                            _uiState.value = MapSearchState.Success(apiResult.data.map { it.mapToPresenter() })

                        is ApiResult.Error ->
                            _uiState.value = MapSearchState.Error(apiResult.throwable.localizedMessage)
                    }
                }
        }
    }
}