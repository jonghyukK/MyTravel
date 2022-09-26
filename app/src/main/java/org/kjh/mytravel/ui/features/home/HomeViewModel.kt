package org.kjh.mytravel.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.data.model.BookmarkModel
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetHomeBannersUseCase
import org.kjh.domain.usecase.GetLatestPostUseCase
import org.kjh.domain.usecase.GetPlaceRankingUseCase
import org.kjh.mytravel.model.BannerItemUiState
import org.kjh.mytravel.model.LatestPostItemUiState
import org.kjh.mytravel.model.PlaceRankingItemUiState
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.common.UiState
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
    val getBannerUseCase      : GetHomeBannersUseCase,
    val getPlaceRankingUseCase: GetPlaceRankingUseCase,
    val getLatestPostUseCase  : GetLatestPostUseCase,
    val eventHandler          : EventHandler
): ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private val _bannersUiState: MutableStateFlow<UiState<List<BannerItemUiState>>> =
        MutableStateFlow(UiState.Init)
    val bannersUiState = _bannersUiState.asStateFlow()

    private val _rankingsUiState: MutableStateFlow<UiState<List<PlaceRankingItemUiState>>> =
        MutableStateFlow(UiState.Init)
    val rankingsUiState = _rankingsUiState.asStateFlow()

    private val _latestPostsUiState: MutableStateFlow<PagingData<LatestPostUiState>?> =
        MutableStateFlow(null)
    val latestPostsUiState = _latestPostsUiState.asStateFlow()

    val accept: (Event) -> Unit = { action ->
        viewModelScope.launch { eventHandler.emitEvent(action)}
    }

    init {
        fetchBanners()
        fetchRankings()
        fetchLatestPostPagingData()
        handleEvent()
    }

    private fun fetchBanners() {
        viewModelScope.launch {
            getBannerUseCase().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _bannersUiState.value = UiState.Loading

                    is ApiResult.Success ->
                        _bannersUiState.value =
                            UiState.Success(apiResult.data.map { it.mapToPresenter() })

                    is ApiResult.Error -> {
                        Logger.e("${apiResult.throwable.message}")
                        _bannersUiState.value =
                            UiState.Error(
                                errorMsg = "occur Error [Fetch Banner API]",
                                errorAction = { initBannerUiState() }
                            )
                    }
                }
            }
        }
    }

    private fun fetchRankings() {
        viewModelScope.launch {
            getPlaceRankingUseCase()
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _rankingsUiState.value = UiState.Loading

                        is ApiResult.Success ->
                            _rankingsUiState.value =
                                UiState.Success(apiResult.data.map { it.mapToPresenter() })

                        is ApiResult.Error -> {
                            Logger.e("${apiResult.throwable.message}")
                            _rankingsUiState.value =
                                UiState.Error(
                                    errorMsg = "occur Error [Fetch Ranking API]",
                                    errorAction = { initRankingUiState() }
                                )
                        }
                    }
                }
        }
    }

    private fun fetchLatestPostPagingData() {
        viewModelScope.launch {
            getLatestPostUseCase().map { pagingData ->
                pagingData.map { entity ->
                    val item = entity.mapToPresenter(
                        onBookmarkAction = { accept(Event.RequestUpdateBookmark(entity.placeName)) },
                        onDeleteDayLogAction = { accept(Event.RequestDeleteDayLog(entity.postId)) }
                    )
                    LatestPostUiState.PagingItem(item)
                }.insertSeparators { before, _ ->
                    when (before) {
                        null -> LatestPostUiState.HeaderItem
                        else -> null
                    }
                }
            }
                .cachedIn(viewModelScope)
                .collectLatest {
                    _latestPostsUiState.value = it
                }
        }
    }

    private fun handleEvent() {
        viewModelScope.launch {
            eventHandler.event.collect { event ->
                when (event) {
                    is Event.LoginEvent,
                    is Event.LogoutEvent -> refreshLatestPosts(true)

                    is Event.DeleteDayLog -> filterDeletedDayLog(event.postId)

                    is Event.UpdateBookmark -> updateDayLogBookmarked(event.bookmarks)
                    else -> {}
                }
            }
        }
    }

    private fun filterDeletedDayLog(deletedDayLogId: Int) {
        _latestPostsUiState.getAndUpdate { pagingData ->
            pagingData?.filter {
                it is LatestPostUiState.PagingItem && it.item.postId != deletedDayLogId
            }
        }
    }

    private fun updateDayLogBookmarked(myBookmarks: List<BookmarkModel>) {
        _latestPostsUiState.getAndUpdate { pagingData ->
            pagingData?.map { currentUiState ->
                if (currentUiState is LatestPostUiState.PagingItem) {
                    val updatedBookmarkItem = currentUiState.item.copy(
                        isBookmarked = myBookmarks.any { currentUiState.item.placeName == it.placeName }
                    )

                    currentUiState.copy(item = updatedBookmarkItem)
                } else {
                    currentUiState
                }
            }
        }
    }

    fun refreshAll() {
        fetchBanners()
        fetchRankings()
        refreshLatestPosts(true)
    }

    fun refreshLatestPosts(value: Boolean) {
        _homeUiState.update { it.copy(refreshLatestPosts = value) }
    }

    private fun initBannerUiState() {
        _bannersUiState.value = UiState.Init
    }

    private fun initRankingUiState() {
        _rankingsUiState.value = UiState.Init
    }

    val updateCollapsed = fun(value: Boolean) {
        _homeUiState.update {
            it.copy(hasScrolledForToolbar = value)
        }
    }
}


data class HomeUiState(
    val hasScrolledForToolbar: Boolean = false,
    val refreshLatestPosts   : Boolean = false
)

sealed class LatestPostUiState {
    object HeaderItem: LatestPostUiState()
    data class PagingItem(val item: LatestPostItemUiState): LatestPostUiState()
}