package org.kjh.mytravel.ui.features.login

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
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
    private val makeLoginRequestUseCase: MakeLoginRequestUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Init)
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
                        _uiState.value = LoginUiState.Loading

                    is ApiResult.Success ->
                        _uiState.value = LoginUiState.Success

                    is ApiResult.Error -> {
                        val errorMsg = apiResult.throwable.message ?: "Unknown Error"
                        Logger.e(errorMsg)

                        _uiState.value = LoginUiState.Failure(errorMsg)
                    }
                }
            }
        }
    }
}

sealed class LoginUiState {
    object Init: LoginUiState()
    object Loading: LoginUiState()
    object Success: LoginUiState()
    data class Failure(val errorMsg: String): LoginUiState()

    fun isLoading() = this is Loading
    fun isFailure() = if (this is Failure) this.errorMsg else null
}