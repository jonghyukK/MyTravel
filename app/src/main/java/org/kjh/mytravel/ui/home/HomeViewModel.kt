package org.kjh.mytravel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetHomeBannersUseCase
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.GetPlaceRankingUseCase
import com.example.domain2.usecase.GetRecentPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Banner
import org.kjh.mytravel.model.PlaceWithRanking
import org.kjh.mytravel.model.UiState
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.utils.ErrorMsg
import javax.inject.Inject

/**
 * MyTravel
 * Class: HomeViewModel
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getHomeBannersUseCase: GetHomeBannersUseCase,
    val getRecentPostsUseCase: GetRecentPostsUseCase,
    val getPlaceRankingUseCase: GetPlaceRankingUseCase,
    val loginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {

    // Home Banners UiState.
    private val _homeBannersUiState: MutableStateFlow<UiState<List<Banner>>> = MutableStateFlow(
        UiState.Loading)
    val homeBannersUiState = _homeBannersUiState.asStateFlow()

    // Home Ranking UiState.
    private val _homeRankingsUiState: MutableStateFlow<UiState<List<PlaceWithRanking>>> = MutableStateFlow(
        UiState.Loading)
    val homeRankingsUiState = _homeRankingsUiState.asStateFlow()

    private val _refreshLatestPostsPagingData = MutableStateFlow(false)
    val refreshLatestPostsPagingData = _refreshLatestPostsPagingData.asStateFlow()

    val scope: CoroutineScope
    get() = viewModelScope

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
                        is ApiResult.Loading ->
                            _homeBannersUiState.value = UiState.Loading

                        is ApiResult.Success -> {
                            val data = apiResult.data.map { it.mapToPresenter() }
                            _homeBannersUiState.value = UiState.Success(data)
                        }

                        is ApiResult.Error ->
                            _homeBannersUiState.value = UiState.Error(ErrorMsg.ERROR_BANNER_API)
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
                        is ApiResult.Loading ->
                            _homeRankingsUiState.value = UiState.Loading

                        is ApiResult.Success -> {
                            val data = apiResult.data.map { it.mapToPresenter() }
                            _homeRankingsUiState.value = UiState.Success(data)
                        }

                        is ApiResult.Error ->
                            _homeRankingsUiState.value = UiState.Error(ErrorMsg.ERROR_PLACE_RANKING_API)
                    }
                }
        }
    }

    // API - Home Latest Posts Paging Data.
    val latestPostsPagingData = getRecentPostsUseCase()
        .map { pagingData -> pagingData.map { it.mapToPresenter() } }
        .cachedIn(viewModelScope)

    fun initHomeBannersUiState() {
        _homeBannersUiState.value = UiState.Init
    }

    fun initPlaceRankingsUiState() {
        _homeRankingsUiState.value = UiState.Init
    }

    fun refreshLatestPosts(value: Boolean) {
        _refreshLatestPostsPagingData.value = value
    }
}