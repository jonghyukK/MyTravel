package org.kjh.mytravel.ui.features.signup

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.MakeSignUpRequestUseCase
import org.kjh.mytravel.utils.EXIST_EMAIL
import org.kjh.mytravel.utils.InputValidator
import org.kjh.mytravel.utils.InputValidator.isValidateEmail
import org.kjh.mytravel.utils.InputValidator.isValidateNickname
import org.kjh.mytravel.utils.InputValidator.isValidatePw
import javax.inject.Inject


/**
 * MyTravel
 * Class: SignUpViewModel
 * Created by mac on 2022/01/13.
 *
 * Description:
 */

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val makeSignUpRequestUseCase: MakeSignUpRequestUseCase,
    private val eventHandler: EventHandler
): ViewModel() {

    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    // Two-way Binding.
    val email   : MutableLiveData<String> = MutableLiveData()
    val pw      : MutableLiveData<String> = MutableLiveData()
    val nickName: MutableLiveData<String> = MutableLiveData()

    val enableSignUp =
        combine(email.asFlow(), pw.asFlow(), nickName.asFlow()) { email, pw, nickName ->
            isValidateEmail(email)
                    && isValidatePw(pw)
                    && isValidateNickname(nickName)
        }.asLiveData()

    fun requestSignUp() {
        viewModelScope.launch {
            makeSignUpRequestUseCase(
                email     = email.value.toString(),
                pw        = pw.value.toString(),
                nickName  = nickName.value.toString()
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _uiState.value = SignUpUiState(isLoading = true)

                    is ApiResult.Success ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isRegistered = true,
                                apiResultError = null
                            )
                        }

                    is ApiResult.Error -> {
                        var errorMsg = apiResult.throwable.message
                        when (errorMsg) {
                            EXIST_EMAIL ->
                                errorMsg = InputValidator.Email.ERROR_EXIST_EMAIL.errorMsg
                            else -> {
                                errorMsg = null
                                eventHandler.emitEvent(
                                    Event.ApiError("occur Error [Request SignUp API]")
                                )
                            }
                        }

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isRegistered = false,
                                apiResultError = errorMsg
                            )
                        }
                    }
                }
            }
        }
    }
}

data class SignUpUiState(
    val isLoading       : Boolean = false,
    val isRegistered    : Boolean = false,
    val apiResultError  : String? = null
)