package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MyTravel
 * Class: NotLoginViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

class NotLoginViewModel: ViewModel() {

    data class LoginOrSignUpUiState(
        val isLoginOrSignUpSuccess: Boolean = false
    )

    private val _uiState: MutableStateFlow<LoginOrSignUpUiState> = MutableStateFlow(LoginOrSignUpUiState())
    val uiState: StateFlow<LoginOrSignUpUiState> = _uiState.asStateFlow()

    fun setLoginOrSignUpState() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoginOrSignUpSuccess = true)
            }
        }
    }
}