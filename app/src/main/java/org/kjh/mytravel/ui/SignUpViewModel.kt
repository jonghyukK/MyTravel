package org.kjh.mytravel.ui

import android.util.Patterns
import androidx.lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.InputValidator
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidateNickName
import org.kjh.mytravel.InputValidator.isValidatePw


/**
 * MyTravel
 * Class: SignUpViewModel
 * Created by mac on 2022/01/13.
 *
 * Description:
 */


data class SignUpUiState(
    val emailError      : String? = null,
    val pwError         : String? = null,
    val nickNameError   : String? = null,
    val isRegistered    : Boolean = false,
    val isLoading       : Boolean = false,
)

fun String.isValidPattern(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

class SignUpViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    val email   : MutableLiveData<String> = MutableLiveData()
    val pw      : MutableLiveData<String> = MutableLiveData()
    val nickName: MutableLiveData<String> = MutableLiveData()

    val enableSignUp = combine(email.asFlow(), pw.asFlow(), nickName.asFlow()) { email, pw, nickName ->
        isValidateEmail(email) == InputValidator.EMAIL.VALIDATE
                && isValidatePw(pw) == InputValidator.PW.VALIDATE
                && isValidateNickName(nickName) == InputValidator.NICKNAME.VALIDATE
    }.asLiveData()

    fun makeRequestSignUp() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    isLoading     = true,
                    emailError    = null,
                    pwError       = null,
                    nickNameError = null
                )
            }

            delay(3000)

            _uiState.update { state ->
                state.copy(
                    isLoading    = false,
                    isRegistered = true
                )
            }
        }
    }
}