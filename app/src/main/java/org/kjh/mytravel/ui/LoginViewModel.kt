package org.kjh.mytravel.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.DataStoreManager
import org.kjh.mytravel.InputValidator
import org.kjh.mytravel.InputValidator.isValidateEmail
import org.kjh.mytravel.InputValidator.isValidatePw
import org.kjh.mytravel.domain.usecase.MakeLoginRequestUseCase
import org.kjh.mytravel.domain.Result
import javax.inject.Inject

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
    val loginError     : String? = null,
    val isLoggedIn     : Boolean = false,
    val isLoading      : Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val makeLoginRequestUseCase: MakeLoginRequestUseCase,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
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
            makeLoginRequestUseCase
                .execute(email.value.toString(), pw.value.toString())
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = true,
                                    loginError = null,
                                    emailError = null,
                                    pwError    = null
                                )
                            }
                        }
                        is Result.Success -> {
                            if (result.data.isLoggedIn)
                                dataStoreManager.saveMyEmail(email.value.toString())

                            _uiState.update {
                                it.copy(
                                    isLoading  = false,
                                    isLoggedIn = result.data.isLoggedIn,
                                    loginError = result.data.errorMsg
                                )
                            }
                        }
                        is Result.Error -> {}
                    }
                }
        }
    }
}