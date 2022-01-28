package org.kjh.mytravel.ui.profile

import android.util.Patterns
import androidx.lifecycle.*
import com.example.domain.entity.ApiResult
import com.example.domain.entity.LoginInfoPreferences
import com.example.domain.usecase.MakeSignUpRequestUseCase
import com.example.domain.usecase.SaveLogInPreferenceUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.InputValidator
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidateNickName
import org.kjh.mytravel.InputValidator.isValidatePw
import javax.inject.Inject


/**
 * MyTravel
 * Class: SignUpViewModel
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

fun String.isValidPattern(): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

data class SignUpUiState(
    val emailError      : String? = null,
    val pwError         : String? = null,
    val nickNameError   : String? = null,
    val isRegistered    : Boolean = false,
    val isLoading       : Boolean = false,
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val makeSignUpRequestUseCase: MakeSignUpRequestUseCase,
    private val saveLogInPreferenceUseCase: SaveLogInPreferenceUseCase
): ViewModel() {
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
            makeSignUpRequestUseCase(
                email     = email.value.toString(),
                pw        = pw.value.toString(),
                nickName  = nickName.value.toString()
            ).collect { result ->

                when (result) {

                    is ApiResult.Loading -> _uiState.value = SignUpUiState(isLoading = true)
                    is ApiResult.Success -> {
                        if (result.data.result) {
                            saveLoginPreference()
                        }

                        _uiState.value =
                            SignUpUiState(
                                isRegistered = result.data.result,
                                emailError   = result.data.errorMsg
                            )
                    }
                    is ApiResult.Error -> { }
                }
            }
        }
    }


    private fun saveLoginPreference() {
        viewModelScope.launch {
            saveLogInPreferenceUseCase(
                LoginInfoPreferences(email.value.toString(), true)
            )
        }
    }
}