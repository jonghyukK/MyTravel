package org.kjh.mytravel.ui

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.InputValidator
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidatePw

/**
 * MyTravel
 * Class: LoginViewModel
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

data class LoginUiState(
    val emailError     : String? = null,
    val pwError        : String? = null,
    val isLoggedIn     : Boolean = false,
    val isLoading      : Boolean = false
)

class LoginViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    val email   : MutableLiveData<String> = MutableLiveData()
    val pw      : MutableLiveData<String> = MutableLiveData()

    val enableLogin = combine(email.asFlow(), pw.asFlow()) { email, pw ->
        isValidateEmail(email) == InputValidator.EMAIL.VALIDATE
                && isValidatePw(pw) == InputValidator.PW.VALIDATE
    }.asLiveData()

    fun makeRequestLogin() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading   = true,
                    emailError  = null,
                    pwError     = null
                )
            }

            _uiState.update { state ->
                state.copy(
                    isLoading  = false,
                    isLoggedIn = true,
                )
            }
        }
    }
}