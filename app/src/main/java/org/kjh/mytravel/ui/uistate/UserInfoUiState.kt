package org.kjh.mytravel.ui.uistate

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: UserUiState
 * Created by mac on 2022/01/15.
 *
 * Description:
 */
data class UserInfoUiState(
    val userId: Long,
    val userEmail: String,
    val profileImg: Int,
    val introduce: String,
    val postCount: Int,
    val followerCount: Int,
    val followingCount: Int,
    val postItems: List<PostItemUiState> = listOf()
)

val tempUserItemList = listOf(
    UserInfoUiState(
        91,
        "saz300@naver.com",
        R.drawable.temp_img_seoul,
        "안녕하세요, 처음이에요 ㅎㅎ",
        3,
        22,
        44,
        tempPostItemList
    ),
    UserInfoUiState(
        92,
        "saz400@naver.com",
        R.drawable.temp_img_gyeongsangdo,
        "안녕하세요, 경상도에용 ㅎ",
        20,
        212,
        424,
        tempPostItemList
    ),
    UserInfoUiState(
        93,
        "saz500@naver.com",
        R.drawable.temp_img_jeju,
        "안녕하세요, 제주에용 ㅎㅎ",
        3,
        22,
        44,
        tempPostItemList
    ),
)

val tempUserInfoItemsFlow: Flow<UserInfoUiState> = flow {
    emit(tempUserItemList[0])
}