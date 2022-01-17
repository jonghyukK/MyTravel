package org.kjh.mytravel.ui.uistate

/**
 * MyTravel
 * Class: UserPageUiState
 * Created by mac on 2022/01/15.
 *
 * Description:
 */
data class UserPageUiState(
    val userItem: UserInfoUiState,
    val isFollowing: Boolean = false,
)