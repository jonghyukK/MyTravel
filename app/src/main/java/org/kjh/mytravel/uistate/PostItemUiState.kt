package org.kjh.mytravel.uistate

import androidx.recyclerview.widget.DiffUtil
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: PostItemUiState
 * Created by mac on 2022/01/14.
 *
 * Description:
 */
data class PostItemUiState(
    val postId      : Long,
    val writer      : String,
    val content     : String,
    val date        : String,
    val cityName    : String,
    val placeAddress: String,
    val placeName   : String,
    val profileImg  : Int,
    val postImages  : List<Int> = listOf()
) {
    companion object {
        val DiffCallback = object: DiffUtil.ItemCallback<PostItemUiState>() {
            override fun areItemsTheSame(oldItem: PostItemUiState, newItem: PostItemUiState): Boolean =
                oldItem.postId == newItem.postId

            override fun areContentsTheSame(oldItem: PostItemUiState, newItem: PostItemUiState): Boolean =
                oldItem == newItem
        }
    }
}

val tempPostItemList = listOf(
    PostItemUiState(
        11,
        "saz300@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-05",
        "서울",
        "서울시 중구 세종대로 110",
        "서울시청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul)
    ),
    PostItemUiState(
        12,
        "saz400@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-03",
        "경기도",
        "경기 수원시 팔달구 효원로 1",
        "경기도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi)
    ),
    PostItemUiState(
        13,
        "saz500@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-02",
        "충청도",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청남도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo)
    ),
    PostItemUiState(
        14,
        "saz600@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-01",
        "전라도",
        "전북 전주시 완산구 효자로 225",
        "전라도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado, R.drawable.temp_img_jeonrado)
    ),
    PostItemUiState(
        15,
        "saz700@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-11",
        "강원도",
        "강원 춘천시 중앙로 1",
        "강원도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo, R.drawable.temp_img_gangwondo)
    ),
    PostItemUiState(
        16,
        "saz800@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-12",
        "경상도",
        "경북 안동시 풍천면 도청대로 455",
        "경상도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo, R.drawable.temp_img_gyeongsangdo)
    ),
    PostItemUiState(
        17,
        "saz900@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-15",
        "제주도",
        "제주 제주시 문연로 6",
        "제주도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju, R.drawable.temp_img_jeju)
    ),
    PostItemUiState(
        18,
        "saz300@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-05",
        "서울",
        "서울시 중구 세종대로 110",
        "서울시청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul, R.drawable.temp_img_seoul)
    ),
    PostItemUiState(
        19,
        "saz400@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-03",
        "경기도",
        "경기 수원시 팔달구 효원로 1",
        "경기도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi, R.drawable.temp_img_gyeonggi)
    ),
    PostItemUiState(
        20,
        "saz500@naver.com",
        "음.. 이곳은 제가 정말 자주가는 곳인데요, 괜찮아요 ㅎㅎ",
        "2022-01-02",
        "충청도",
        "충남 홍성군 홍북읍 충남대로 21",
        "충청남도청",
        R.drawable.ic_launcher_background,
        listOf(R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo, R.drawable.temp_img_chungcheongdo)
    ),
)