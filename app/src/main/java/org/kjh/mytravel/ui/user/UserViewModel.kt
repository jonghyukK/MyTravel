package org.kjh.mytravel.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.ui.uistate.UserInfoUiState
import org.kjh.mytravel.ui.uistate.tempUserInfoItemsFlow

/**
 * MyTravel
 * Class: UserViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

data class UserUiState(
    val userItem: UserInfoUiState? = null,
    val isFollowing: Boolean = false
)

class UserViewModel(
    private val initUserEmail: String
): ViewModel() {

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState : StateFlow<UserUiState> = _userUiState

    init {
        viewModelScope.launch {
            val userInfo = tempUserInfoItemsFlow
            val isFollowing = false

            _userUiState.update {
                it.copy(
                    userItem = userInfo.last(),
                    isFollowing = isFollowing
                )
            }
        }
    }
}