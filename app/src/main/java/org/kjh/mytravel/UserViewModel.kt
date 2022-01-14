package org.kjh.mytravel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.kjh.mytravel.uistate.UserPageUiState
import org.kjh.mytravel.uistate.tempUserItemList

/**
 * MyTravel
 * Class: UserViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */
class UserViewModel: ViewModel() {

    private val _userPageUiState = MutableLiveData<UserPageUiState>()
    val userPageUiState: LiveData<UserPageUiState> = _userPageUiState

    fun getUser(email: String) {
        viewModelScope.launch {
            _userPageUiState.value =
                UserPageUiState(
                    tempUserItemList.find { it.userEmail == email } ?: tempUserItemList[0],
                    true
                )
        }
    }

}