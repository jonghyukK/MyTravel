package org.kjh.mytravel.ui

import android.util.Patterns
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.DataStoreManager
import org.kjh.mytravel.InputValidator
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidateNickName
import org.kjh.mytravel.InputValidator.isValidatePw
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.usecase.MakeSignUpRequestUseCase
import javax.inject.Inject


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


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val makeSignUpRequestUseCase: MakeSignUpRequestUseCase,
    private val dataStoreManager: DataStoreManager
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
            makeSignUpRequestUseCase
                .execute(email.value.toString(), pw.value.toString(), nickName.value.toString())
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading  = true,
                                    emailError = null,
                                )
                            }
                        }
                        is Result.Success -> {
                            if (result.data.isRegistered) {
                                dataStoreManager.saveMyEmail(email.value.toString())
                            }

                            _uiState.update {
                                it.copy(
                                    isLoading    = false,
                                    isRegistered = result.data.isRegistered,
                                    emailError   = result.data.errorMsg
                                )
                            }
                        }
                        is Result.Error -> { }
                    }
                }
        }
    }
}