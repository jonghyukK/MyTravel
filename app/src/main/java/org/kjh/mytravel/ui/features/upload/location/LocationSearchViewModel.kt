package org.kjh.mytravel.ui.features.upload.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.SearchMapUseCase
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.common.UiState
import javax.inject.Inject

/**
 * MyTravel
 * Class: MapSearchViewModel
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val searchMapUseCase: SearchMapUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<MapQueryItem>>> = MutableStateFlow(UiState.Init)
    val uiState = _uiState.asStateFlow()

    val searchQuery = MutableLiveData<String>()

    fun requestSearchPlaceByQuery() {
        viewModelScope.launch {
            searchMapUseCase(searchQuery.value.toString())
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _uiState.value = UiState.Loading

                        is ApiResult.Success ->
                            _uiState.value = UiState.Success(apiResult.data.map { it.mapToPresenter() })

                        is ApiResult.Error -> {
                            Logger.e("${apiResult.throwable.message}")
                            _uiState.value = UiState.Error(Throwable("occur Error [request Search Place API]"))
                        }
                    }
                }
        }
    }

    fun initUiState() {
        _uiState.value = UiState.Init
    }
}