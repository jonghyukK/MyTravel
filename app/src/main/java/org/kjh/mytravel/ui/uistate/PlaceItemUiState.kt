package org.kjh.mytravel.ui.uistate

import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: PlaceItemUiState
 * Created by mac on 2022/01/14.
 *
 * Description:
 */
data class PlaceItemUiState(
    val placeId     : Long,
    val placeName   : String,
    val placeAddress: String,
    val cityName    : String,
    val placeImages : List<Int> = listOf(),
    val postItems   : List<PostItemUiState> = listOf()
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<PlaceItemUiState>() {
            override fun areItemsTheSame(
                oldItem: PlaceItemUiState,
                newItem: PlaceItemUiState
            ): Boolean =
                oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: PlaceItemUiState,
                newItem: PlaceItemUiState
            ): Boolean =
                oldItem == newItem
        }
    }
}

val tempPlaceItemList = listOf(
    PlaceItemUiState(
        111,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItemUiState(
        112,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItemUiState(
        113,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
    PlaceItemUiState(
        114,
        "전라도청",
        "전북 전주시 완산구 효자로 225",
        "전라도",
        listOf(R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado),
        tempPostItemList
    ),
    PlaceItemUiState(
        115,
        "강원도청",
        "강원 춘천시 중앙로 1",
        "강원도",
        listOf(R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo),
        tempPostItemList
    ),
    PlaceItemUiState(
        116,
        "경상도청",
        "경북 안동시 풍천면 도청대로 455",
        "경상도",
        listOf(R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo),
        tempPostItemList
    ),
    PlaceItemUiState(
        117,
        "제주도청",
        "제주 제주시 문연로 6",
        "제주도",
        listOf(R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju),
        tempPostItemList
    ),
    PlaceItemUiState(
        118,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItemUiState(
        119,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItemUiState(
        120,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
    PlaceItemUiState(
        121,
        "제주도청",
        "제주 제주시 문연로 6",
        "제주도",
        listOf(R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju),
        tempPostItemList
    ),
    PlaceItemUiState(
        122,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItemUiState(
        123,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItemUiState(
        124,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
)

val tempPlaceItemList2 = listOf(
    PlaceItemUiState(
        111121,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItemUiState(
        112,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItemUiState(
        113,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
    PlaceItemUiState(
        114,
        "전라도청",
        "전북 전주시 완산구 효자로 225",
        "전라도",
        listOf(R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado),
        tempPostItemList
    ),
    PlaceItemUiState(
        115,
        "강원도청",
        "강원 춘천시 중앙로 1",
        "강원도",
        listOf(R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo),
        tempPostItemList
    ),
    PlaceItemUiState(
        116,
        "경상도청",
        "경북 안동시 풍천면 도청대로 455",
        "경상도",
        listOf(R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo),
        tempPostItemList
    ),
    PlaceItemUiState(
        117,
        "제주도청",
        "제주 제주시 문연로 6",
        "제주도",
        listOf(R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju),
        tempPostItemList
    ),
    PlaceItemUiState(
        118,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItemUiState(
        119,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItemUiState(
        120,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
    PlaceItemUiState(
        121,
        "제주도청",
        "제주 제주시 문연로 6",
        "제주도",
        listOf(R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju),
        tempPostItemList
    ),
    PlaceItemUiState(
        122,
        "서울시청",
        "서울시 중구 세종대로 110",
        "서울",
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul),
        tempPostItemList
    ),
    PlaceItemUiState(
        123,
        "경기도청",
        "경기 수원시 팔달구 효원로 1",
        "경기도",
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi),
        tempPostItemList
    ),
    PlaceItemUiState(
        124,
        "충청도청",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청도",
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo),
        tempPostItemList
    ),
)

val tempPlaceItemsFlow: Flow<List<PlaceItemUiState>> = flow {
    emit(tempPlaceItemList)
}
