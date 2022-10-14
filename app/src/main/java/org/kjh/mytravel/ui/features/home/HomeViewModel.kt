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
import org.kjh.domain.usecase.GetLatestDayLogUseCase
import org.kjh.domain.usecase.GetPlaceRankingUseCase
import org.kjh.mytravel.model.BannerItemUiState
import org.kjh.mytravel.model.LatestDayLogItemUiState
import org.kjh.mytravel.model.PlaceRankingItemUiState
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.model.common.PagingWithHeaderUiModel
import org.kjh.mytravel.model.common.UiState
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
    val getLatestDayLogUseCase: GetLatestDayLogUseCase,
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

    private val _latestDayLogsUiState: MutableStateFlow<PagingData<PagingWithHeaderUiModel<LatestDayLogItemUiState>>?> =
        MutableStateFlow(null)
    val latestDayLogsUiState = _latestDayLogsUiState.asStateFlow()

    val sendEvent: (Event) -> Unit = { action ->
        viewModelScope.launch { eventHandler.emitEvent(action)}
    }

    val refreshActionForAll = fun() {
        fetchBanners()
        fetchRankings()
        refreshLatestDayLogs(true)
    }

    val updateCollapsed = fun(value: Boolean) {
        _homeUiState.update {
            it.copy(hasScrolled = value)
        }
    }

    init {
        fetchBanners()
        fetchRankings()
        fetchLatestDayLogPagingData()
        handleEvent()
    }

    private fun fetchBanners() {
        viewModelScope.launch {
            getBannerUseCase().collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _bannersUiState.value = UiState.Loading

                    is ApiResult.Success ->
                        _bannersUiState.value = UiState.Success(
                            apiResult.data.map { it.mapToPresenter() })

                    is ApiResult.Error -> {
                        Logger.e("${apiResult.throwable.message}")
                        _bannersUiState.value = UiState.Error(
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
                            _rankingsUiState.value = UiState.Success(
                                apiResult.data.map { it.mapToPresenter() })

                        is ApiResult.Error -> {
                            Logger.e("${apiResult.throwable.message}")
                            _rankingsUiState.value = UiState.Error(
                                    errorMsg = "occur Error [Fetch Ranking API]",
                                    errorAction = { initRankingUiState() }
                                )
                        }
                    }
                }
        }
    }

    private fun fetchLatestDayLogPagingData() {
        viewModelScope.launch {
            getLatestDayLogUseCase()
                .map { pagingData ->
                    pagingData.map { entity ->
                        val item = entity.mapToPresenter(
                            onBookmarkAction = { sendEvent(Event.RequestUpdateBookmark(entity.placeName)) },
                            onDeleteDayLogAction = { sendEvent(Event.RequestDeleteDayLog(entity.dayLogId)) }
                        )
                        PagingWithHeaderUiModel.PagingItem(item)
                    }.insertSeparators { before, after ->
                        if (after == null) {
                            return@insertSeparators null
                        }
                        when (before) {
                            null -> PagingWithHeaderUiModel.HeaderItem
                            else -> null
                        }
                    }
                }
                .cachedIn(viewModelScope)
                .collectLatest {
                    _latestDayLogsUiState.value = it
                }
        }
    }

    private fun handleEvent() {
        viewModelScope.launch {
            eventHandler.event.collect { event ->
                when (event) {
                    is Event.LoginEvent,
                    is Event.LogoutEvent -> refreshLatestDayLogs(true)
                    is Event.DeleteDayLog -> filterDeletedDayLog(event.dayLogId)
                    is Event.UpdateBookmark -> updateDayLogBookmarked(event.bookmarks)
                    else -> {}
                }
            }
        }
    }

    private fun filterDeletedDayLog(deletedDayLogId: Int) {
        _latestDayLogsUiState.getAndUpdate { pagingData ->
            pagingData?.filter {
                it is PagingWithHeaderUiModel.PagingItem && it.item.dayLogId != deletedDayLogId
            }
        }
    }

    private fun updateDayLogBookmarked(myBookmarks: List<BookmarkModel>) {
        _latestDayLogsUiState.getAndUpdate { pagingData ->
            pagingData?.map { currentPagingItem ->
                if (currentPagingItem is PagingWithHeaderUiModel.PagingItem) {
                    val updatedDayLogItem = currentPagingItem.item.copy(
                        isBookmarked = myBookmarks.any { currentPagingItem.item.placeName == it.placeName }
                    )

                    currentPagingItem.copy(item = updatedDayLogItem)
                } else {
                    currentPagingItem
                }
            }
        }
    }

    fun refreshLatestDayLogs(doRefresh: Boolean) {
        _homeUiState.update {
            it.copy(refreshLatestDayLogs = doRefresh)
        }
    }

    private fun initBannerUiState() {
        _bannersUiState.value = UiState.Init
    }

    private fun initRankingUiState() {
        _rankingsUiState.value = UiState.Init
    }
}

data class HomeUiState(
    val hasScrolled: Boolean = false,
    val refreshLatestDayLogs : Boolean = false
)