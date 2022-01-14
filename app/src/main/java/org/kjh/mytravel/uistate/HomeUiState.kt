package org.kjh.mytravel.uistate

/**
 * MyTravel
 * Class: HomeUiState
 * Created by mac on 2022/01/14.
 *
 * Description:
 */
data class HomeUiState(
    val bannerItems     : List<BannerItemUiState> = listOf(),
    val rankingItems    : List<RankingItemUiState> = listOf(),
    val eventItems      : List<EventItemUiState> = listOf(),
    val recentPlaceItems: List<PlaceItemUiState> = listOf()
)