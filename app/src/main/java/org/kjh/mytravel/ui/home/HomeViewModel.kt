package org.kjh.mytravel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetHomeBannersUseCase
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.GetPlaceRankingUseCase
import com.example.domain2.usecase.GetRecentPostsUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Banner
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.base.UiState
import javax.inject.Inject

/**
 * MyTravel
 * Class: HomeViewModel
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */

data class HomeUiState(
    val isCollapsed : Boolean = false,
    val isRefresh   : Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getHomeBannersUseCase: GetHomeBannersUseCase,
    val getRecentPostsUseCase: GetRecentPostsUseCase,
    val getPlaceRankingUseCase: GetPlaceRankingUseCase,
    val loginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {

    private val _homeBannersUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val homeBannersUiState: StateFlow<UiState> = _homeBannersUiState

    private val _homeRankingsUiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val homeRankingsUiState: StateFlow<UiState> = _homeRankingsUiState

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    private var currentRecentPostsPagingData: Flow<PagingData<Post>>? = null

    init {
        getHomeBanners()
        getPlaceRankings()
    }

    // API - home Banners.
    private fun getHomeBanners() {
        viewModelScope.launch {
            getHomeBannersUseCase()
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> {}
                        is ApiResult.Success -> {
                            val data = apiResult.data.map { it.mapToPresenter() }
                            _homeBannersUiState.value = UiState.Success(data)
                        }
                        is ApiResult.Error -> {
                            Logger.e("${apiResult.throwable}")
                            _homeBannersUiState.value = UiState.Error(apiResult.throwable)
                        }
                    }
                }
        }
    }

    // API - home Place Rankings.
    private fun getPlaceRankings() {
        viewModelScope.launch {
            getPlaceRankingUseCase()
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> {}
                        is ApiResult.Success -> {
                            val data = apiResult.data.map { it.mapToPresenter() }
                            _homeRankingsUiState.value = UiState.Success(data)
                        }
                        is ApiResult.Error -> {
                            Logger.e("${apiResult.throwable}")
                            _homeRankingsUiState.value = UiState.Error(apiResult.throwable)
                        }
                    }
                }
        }
    }

    // API - home Recent Posts.
    suspend fun getRecentPostPagingData(): Flow<PagingData<Post>> {
        val lastResult = currentRecentPostsPagingData

        if (lastResult != null) {
            return lastResult
        }

        val newResult = getRecentPostsUseCase(loginPreferenceUseCase().email)
            .map { pagingData ->
                pagingData.map { it.mapToPresenter() }
            }
            .cachedIn(viewModelScope)
        currentRecentPostsPagingData = newResult

        return newResult
    }


    fun refreshRecentPosts(value: Boolean) {
        _homeUiState.update {
            it.copy(isRefresh = value)
        }
    }

    fun updateCollapsingState(value: Boolean) {
        _homeUiState.update {
            it.copy(isCollapsed = value)
        }
    }
}