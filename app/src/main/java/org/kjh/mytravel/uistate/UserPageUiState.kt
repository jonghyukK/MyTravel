package org.kjh.mytravel.uistate

/**
 * MyTravel
 * Class: UserPageUiState
 * Created by mac on 2022/01/15.
 *
 * Description:
 */
data class UserPageUiState(
    val userItem: UserUiState,
    val isFollowing: Boolean = false,
)