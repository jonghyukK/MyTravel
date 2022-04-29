package org.kjh.mytravel.ui.profile.upload

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.SearchMapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.common.ErrorMsg
import javax.inject.Inject

/**
 * MyTravel
 * Class: MapSearchViewModel
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

@HiltViewModel
class MapSearchViewModel @Inject constructor(
    private val searchMapUseCase: SearchMapUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<MapQueryItem>>> = MutableStateFlow(UiState.Init)
    val uiState = _uiState.asStateFlow()

    val searchQuery = MutableLiveData<String>()

    fun makeSearchPlace() {
        viewModelScope.launch {
            searchMapUseCase(searchQuery.value.toString())
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _uiState.value = UiState.Loading

                        is ApiResult.Success ->
                            _uiState.value = UiState.Success(apiResult.data.map { it.mapToPresenter() })

                        is ApiResult.Error ->
                            _uiState.value = UiState.Error(ErrorMsg.ERROR_MAP_SEARCH_FAIL)
                    }
                }
        }
    }
}