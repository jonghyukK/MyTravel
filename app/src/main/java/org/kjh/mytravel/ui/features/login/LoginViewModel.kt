package org.kjh.mytravel.ui.features.login

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.MakeLoginRequestUseCase
import org.kjh.mytravel.utils.InputValidator
import org.kjh.mytravel.utils.InputValidator.isValidateEmail
import org.kjh.mytravel.utils.InputValidator.isValidatePw
import org.kjh.mytravel.utils.NOT_EXIST_EMAIL
import org.kjh.mytravel.utils.NOT_MATCH_PW
import javax.inject.Inject

/**
 * MyTravel
 * Class: LoginViewModel
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val makeLoginRequestUseCase: MakeLoginRequestUseCase,
    private val eventHandler: EventHandler
): ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Two-way Binding.
    val email: MutableLiveData<String> = MutableLiveData()
    val pw   : MutableLiveData<String> = MutableLiveData()

    val enableLogin =
        combine(email.asFlow(), pw.asFlow()) { email, pw ->
            isValidateEmail(email) && isValidatePw(pw)
        }.asLiveData()

    fun requestLogin() {
        viewModelScope.launch {
            makeLoginRequestUseCase(
                email = email.value.toString(),
                pw    = pw.value.toString()
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _uiState.value = LoginUiState(isLoading = true)

                    is ApiResult.Success ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                apiResultError = null
                            )
                        }

                    is ApiResult.Error -> {
                        var errorMsg = apiResult.throwable.message
                        when (errorMsg) {
                            NOT_EXIST_EMAIL ->
                                errorMsg = InputValidator.Email.ERROR_NOT_EXIST_EMAIL.errorMsg
                            NOT_MATCH_PW ->
                                errorMsg = InputValidator.Pw.ERROR_WRONG_PW.errorMsg
                            else -> {
                                errorMsg = null
                                eventHandler.emitEvent(
                                    Event.ApiError("occur Error [Request Login API]")
                                )
                            }
                        }

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = false,
                                apiResultError = errorMsg
                            )
                        }
                    }
                }
            }
        }
    }
}

data class LoginUiState(
    val isLoading      : Boolean = false,
    val isLoggedIn     : Boolean = false,
    val apiResultError : String? = null
)