package org.kjh.mytravel.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetHomeBannersUseCase
import org.kjh.domain.usecase.GetLoginPreferenceUseCase
import org.kjh.domain.usecase.GetPlaceRankingUseCase
import org.kjh.domain.usecase.GetRecentPostsUseCase
import org.kjh.mytravel.model.Banner
import org.kjh.mytravel.model.PlaceWithRanking
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.GlobalErrorHandler
import javax.inject.Inject

/**
 * MyTravel
 * Class: HomeViewModel
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */

sealed class LatestPostUiModel {
    data class HeaderItem(val headerTitle: String) : LatestPostUiModel()
    data class PostItem(val post: Post): LatestPostUiModel()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getHomeBannersUseCase: GetHomeBannersUseCase,
    val getRecentPostsUseCase: GetRecentPostsUseCase,
    val getPlaceRankingUseCase: GetPlaceRankingUseCase,
    val loginPreferenceUseCase: GetLoginPreferenceUseCase,
    val globalErrorHandler: GlobalErrorHandler
): ViewModel() {

    data class HomeUiState(
        val isLoading: Boolean = true,
        val isCollapsed: Boolean = false,
        val bannerItems : List<Banner> = listOf(),
        val rankingItems: List<PlaceWithRanking> = listOf()
    )

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private val _refreshLatestPostsPagingData = MutableStateFlow(false)
    val refreshLatestPostsPagingData = _refreshLatestPostsPagingData.asStateFlow()

    val latestPostsPagingData = getRecentPostsUseCase()
        .map { pagingData -> pagingData.map { LatestPostUiModel.PostItem(it.mapToPresenter()) }}
        .map {
            it.insertSeparators { before, after ->
                if (after == null)
                    return@insertSeparators null

                if (before == null) {
                    LatestPostUiModel.HeaderItem("최근 올라온 DayLog")
                } else {
                    null
                }
            }
        }
        .cachedIn(viewModelScope)

    init {
        fetchHomeBannerAndRankingItems()
    }

    private fun fetchHomeBannerAndRankingItems() {
        viewModelScope.launch {
            _homeUiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val fetchBanner  = async { getHomeBannersUseCase() }
            val fetchRanking = async { getPlaceRankingUseCase() }

            val bannerResult  = fetchBanner.await()
            val rankingResult = fetchRanking.await()

            combineTransform(bannerResult, rankingResult) { banner, ranking ->
                val bannerItems = when (banner) {
                    is ApiResult.Loading -> emptyList()
                    is ApiResult.Success -> banner.data.map { it.mapToPresenter() }
                    is ApiResult.Error -> {
                        globalErrorHandler.sendError("occur Error [Fetch HomeBanner API]")
                        emptyList()
                    }
                }

                val rankingItems = when (ranking) {
                    is ApiResult.Loading -> emptyList()
                    is ApiResult.Success -> ranking.data.map { it.mapToPresenter() }
                    is ApiResult.Error -> {
                        globalErrorHandler.sendError("occur Error [Fetch Ranking API]")
                        emptyList()
                    }
                }

                emit(HomeUiState(
                    isLoading = false,
                    bannerItems = bannerItems,
                    rankingItems = rankingItems)
                )
            }.collect { uiState ->
                _homeUiState.update {
                    it.copy(
                        isLoading = false,
                        bannerItems = uiState.bannerItems,
                        rankingItems = uiState.rankingItems
                    )
                }
            }
        }
    }

    fun refreshLatestPosts(value: Boolean) {
        _refreshLatestPostsPagingData.value = value
    }

    val updateCollapsed = fun(value: Boolean) {
        _homeUiState.update {
            it.copy(
                isCollapsed = value
            )
        }
    }
}