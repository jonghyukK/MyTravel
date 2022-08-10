package org.kjh.mytravel.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.BannerEntity
import org.kjh.domain.entity.PlaceWithRankEntity
import org.kjh.domain.usecase.GetHomeBannersUseCase
import org.kjh.domain.usecase.GetLoginPreferenceUseCase
import org.kjh.domain.usecase.GetPlaceRankingUseCase
import org.kjh.domain.usecase.GetRecentPostsUseCase
import org.kjh.mytravel.model.Banner
import org.kjh.mytravel.model.PlaceWithRanking
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.GlobalErrorHandler
import org.kjh.mytravel.ui.common.UiState
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

    private val _homeBannersUiState: MutableStateFlow<UiState<List<Banner>>> =
        MutableStateFlow(UiState.Loading)
    val homeBannersUiState = _homeBannersUiState.asStateFlow()

    private val _homeRankingsUiState: MutableStateFlow<UiState<List<PlaceWithRanking>>> =
        MutableStateFlow(UiState.Loading)
    val homeRankingsUiState = _homeRankingsUiState.asStateFlow()

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

    private val _motionProgress: MutableStateFlow<Float> = MutableStateFlow(0f)
    val motionProgress = _motionProgress.asStateFlow()

    init {
        fetchHomeBanners()
        fetchPlaceRankings()
    }

    private fun fetchHomeBanners() {
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

                        is ApiResult.Error -> {
                            val errorMsg = "occur Error [Fetch Banners API]"
                            globalErrorHandler.sendError(errorMsg)
                            _homeBannersUiState.value = UiState.Error(Throwable(errorMsg))
                        }
                    }
                }
        }
    }

    private fun fetchPlaceRankings() {
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

                        is ApiResult.Error -> {
                            val errorMsg = "occur Error [Fetch Ranking API]"
                            globalErrorHandler.sendError(errorMsg)
                            _homeRankingsUiState.value = UiState.Error(Throwable(errorMsg))
                        }
                    }
                }
        }
    }

    fun refreshLatestPosts(value: Boolean) {
        _refreshLatestPostsPagingData.value = value
    }

    fun saveMotionProgress(value: Float) {
        _motionProgress.value = value
    }
}