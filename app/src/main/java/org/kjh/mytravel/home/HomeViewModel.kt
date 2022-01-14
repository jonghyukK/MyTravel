package org.kjh.mytravel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.mytravel.uistate.*


/**
 * MyTravel
 * Class: HomeViewModel
 * Created by mac on 2021/12/31.
 *
 * Description:
 */
class HomeViewModel: ViewModel() {

    private val _homeUiState = MutableLiveData<HomeUiState>()
    val homeUiState: LiveData<HomeUiState> = _homeUiState

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            val bannerItems      = tempBannerItems
            val rankingItems     = tempRankingItems
            val eventItems       = tempEventItems
            val recentPlaceItems = tempPlaceItemList

            _homeUiState.value = HomeUiState(
                bannerItems, rankingItems, eventItems, recentPlaceItems
            )
        }
    }
}