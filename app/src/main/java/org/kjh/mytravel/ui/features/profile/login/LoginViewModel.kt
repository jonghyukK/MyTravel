package org.kjh.mytravel.ui.features.profile.login

import androidx.lifecycle.*
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.MakeLoginRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.features.profile.InputValidator
import org.kjh.mytravel.ui.features.profile.InputValidator.isValidateEmail
import org.kjh.mytravel.ui.features.profile.InputValidator.isValidatePw
import javax.inject.Inject

/**
 * MyTravel
 * Class: LoginViewModel
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

data class LoginUiState(
    val loginError     : String? = null,
    val isLoggedIn     : Boolean = false,
    val isLoading      : Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val makeLoginRequestUseCase : MakeLoginRequestUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    val email : MutableLiveData<String> = MutableLiveData()
    val pw    : MutableLiveData<String> = MutableLiveData()

    val enableLogin = combine(email.asFlow(), pw.asFlow()) { email, pw ->
        isValidateEmail(email) == InputValidator.EMAIL.VALIDATE
                && isValidatePw(pw) == InputValidator.PW.VALIDATE
    }.asLiveData()

    fun makeRequestLogin() {
        viewModelScope.launch {
            makeLoginRequestUseCase(
                email = email.value.toString(),
                pw    = pw.value.toString()
            ).collect { result ->
                when (result) {
                    is ApiResult.Loading -> _uiState.value =
                        LoginUiState(isLoading = true)

                    is ApiResult.Success -> {
                        val loginData = result.data.mapToPresenter()

                        _uiState.value = LoginUiState(
                            isLoading  = false,
                            isLoggedIn = loginData.isLoggedIn,
                            loginError = loginData.errorMsg
                        )
                    }
                    is ApiResult.Error ->
                        _uiState.update {
                            it.copy(
                                isLoading  = false,
                                isLoggedIn = false,
                                loginError = result.throwable.localizedMessage
                            )
                        }
                }
            }
        }
    }
}