package org.kjh.mytravel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.domain.usecase.GetBannersUseCase
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.usecase.GetEventsUseCase
import org.kjh.mytravel.domain.usecase.GetPlaceListUseCase
import org.kjh.mytravel.domain.usecase.GetRankingUseCase
import org.kjh.mytravel.ui.uistate.*
import javax.inject.Inject


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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBannersUseCase: GetBannersUseCase,
    private val getRankingUseCase: GetRankingUseCase,
    private val getEventsUseCase: GetEventsUseCase,
    private val getPlaceListUseCase: GetPlaceListUseCase
): ViewModel() {
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
            getBannersUseCase.execute().collect {
                when (it) {
                    is Result.Success -> {
                        _bannerUiState.value = BannerUiState.Success(it.data.bannerList)
                    }
                }
            }
        }

        viewModelScope.launch {
            getRankingUseCase.execute().collect {
                when (it) {
                    is Result.Success -> {
                        _rankingUiState.value = RankingUiState.Success(it.data.rankingList)
                    }
                }
            }
        }

        viewModelScope.launch {
            getEventsUseCase.execute().collect {
                when (it) {
                    is Result.Success -> {
                        _eventUiState.value = EventUiState.Success(it.data.eventList)
                    }
                }
            }
        }

        viewModelScope.launch {
//            getPlaceListUseCase.execute().collect {
//                when (it) {
//                    is Result.Success -> {
////                        _recentPlaceUiState.value = RecentPlaceUiState.Success(it.data.placeList)
//                    }
//                }
//            }
        }
    }
}