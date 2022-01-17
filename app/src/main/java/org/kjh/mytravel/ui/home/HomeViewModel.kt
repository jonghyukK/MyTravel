package org.kjh.mytravel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.ui.uistate.*


/**
 * MyTravel
 * Class: HomeViewModel
 * Created by mac on 2021/12/31.
 *
 * Description:
 */

sealed class BannerUiState {
    object Loading: BannerUiState()
    data class Success(val banners: List<BannerItemUiState>): BannerUiState()
    data class Error(val exception: Throwable?): BannerUiState()
}

sealed class RankingUiState {
    object Loading: RankingUiState()
    data class Success(val rankingItems: List<RankingItemUiState>): RankingUiState()
    data class Error(val exception: Throwable?): RankingUiState()
}

sealed class EventUiState {
    object Loading: EventUiState()
    data class Success(val eventItems: List<EventItemUiState>): EventUiState()
    data class Error(val exception: Throwable?): EventUiState()
}

sealed class RecentPlaceUiState {
    object Loading: RecentPlaceUiState()
    data class Success(val recentPlaceItems: List<PlaceItemUiState>): RecentPlaceUiState()
    data class Error(val exception: Throwable?): RecentPlaceUiState()
}

class HomeViewModel: ViewModel() {
    private val _bannerUiState = MutableStateFlow<BannerUiState>(BannerUiState.Loading)
    val bannerUiState : StateFlow<BannerUiState> = _bannerUiState

    private val _rankingUiState = MutableStateFlow<RankingUiState>(RankingUiState.Loading)
    val rankingUiState : StateFlow<RankingUiState> = _rankingUiState

    private val _eventUiState = MutableStateFlow<EventUiState>(EventUiState.Loading)
    val eventUiState : StateFlow<EventUiState> = _eventUiState

    private val _recentPlaceUiState = MutableStateFlow<RecentPlaceUiState>(RecentPlaceUiState.Loading)
    val recentPlaceUiState : StateFlow<RecentPlaceUiState> = _recentPlaceUiState


    init {
        viewModelScope.launch {
            tempBannerItemsFlow.collect { item ->
                _bannerUiState.value = BannerUiState.Success(item)
            }
        }

        viewModelScope.launch {
            tempRankingItemsFlow.collect { item ->
                _rankingUiState.value = RankingUiState.Success(item)
            }
        }

        viewModelScope.launch {
            tempEventItemsFlow.collect { item ->
                _eventUiState.value = EventUiState.Success(item)
            }
        }

        viewModelScope.launch {
            tempPlaceItemsFlow.collect { item ->
                _recentPlaceUiState.value = RecentPlaceUiState.Success(item)
            }
        }
    }
}