package org.kjh.mytravel.ui.features.login

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.MakeLoginRequestUseCase
import org.kjh.mytravel.model.Login
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.utils.InputValidator.isValidateEmail
import org.kjh.mytravel.utils.InputValidator.isValidatePw
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
    private val makeLoginRequestUseCase : MakeLoginRequestUseCase
): ViewModel() {

    data class LoginUiState(
        val isLoading      : Boolean = false,
        val isLoggedIn     : Boolean = false,
        val apiResultError : String? = null
    )

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    val email : MutableLiveData<String> = MutableLiveData()
    val pw    : MutableLiveData<String> = MutableLiveData()

    val enableLogin = combine(email.asFlow(), pw.asFlow()) { email, pw ->
            isValidateEmail(email) && isValidatePw(pw)
    }.asLiveData()

    fun requestLogin() {
        viewModelScope.launch {
            makeLoginRequestUseCase(
                email = email.value.toString(),
                pw    = pw.value.toString()
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading -> _uiState.value = LoginUiState(isLoading = true)

                    is ApiResult.Success -> {
                        val result = apiResult.data.mapToPresenter()

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = result.isSuccess,
                                apiResultError = result.loginErrorMsg
                            )
                        }
                    }

                    is ApiResult.Error -> {
                        Logger.e("${apiResult.throwable.message}")

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isLoggedIn = false,
                                apiResultError = "occur Error [request Login API]"
                            )
                        }
                    }
                }
            }
        }
    }
}