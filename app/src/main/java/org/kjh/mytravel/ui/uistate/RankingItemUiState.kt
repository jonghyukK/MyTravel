package org.kjh.mytravel.ui.uistate

import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: RankingItemUiState
 * Created by mac on 2022/01/14.
 *
 * Description:
 */
data class RankingItemUiState(
    val placeId  : Long,
    val placeName: String,
    val cityName : String,
    val placeImg : Int,
    val rank     : Int
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<RankingItemUiState>() {
            override fun areItemsTheSame(
                oldItem: RankingItemUiState,
                newItem: RankingItemUiState
            ): Boolean =
                oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: RankingItemUiState,
                newItem: RankingItemUiState
            ): Boolean =
                oldItem == newItem
        }
    }
}

val tempRankingItems = listOf(
    RankingItemUiState(
        111,
        "서울시청",
        "서울시 중구 세종대로 110",
        R.drawable.temp_img_seoul,
        1
    ),
    RankingItemUiState(
        112,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        R.drawable.temp_img_gyeonggi,
        2
    ),
    RankingItemUiState(
        113,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        R.drawable.temp_img_chungcheongdo,
        3
    ),
    RankingItemUiState(
        114,
        "전라도청",
        "전북 전주시 완산구 효자로 225",
        R.drawable.temp_img_jeonrado,
        4
    ),
    RankingItemUiState(
        115,
        "강원도청",
        "강원 춘천시 중앙로 1",
        R.drawable.temp_img_gangwondo,
        5
    ),
    RankingItemUiState(
        116,
        "경상도청",
        "경북 안동시 풍천면 도청대로 455",
        R.drawable.temp_img_gyeongsangdo,
        6
    ),
    RankingItemUiState(
        117,
        "제주도청",
        "제주 제주시 문연로 6",
        R.drawable.temp_img_jeju,
        7
    )
)

val tempRankingItemsFlow: Flow<List<RankingItemUiState>> = flow {
    emit(tempRankingItems)
}