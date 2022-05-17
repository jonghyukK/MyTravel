package org.kjh.mytravel.ui.features.profile.signup

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.MakeSignUpRequestUseCase
import org.kjh.mytravel.model.SignUp
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.features.profile.InputValidator.isValidateEmail
import org.kjh.mytravel.ui.features.profile.InputValidator.isValidateNickname
import org.kjh.mytravel.ui.features.profile.InputValidator.isValidatePw
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
    private val makeSignUpRequestUseCase: MakeSignUpRequestUseCase
): ViewModel() {

    data class SignUpUiState(
        val isLoading       : Boolean = false,
        val isRegistered    : Boolean = false,
        val apiResultError  : String? = null
    )

    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

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
                    is ApiResult.Loading -> handleLoading()

                    is ApiResult.Success -> handleSuccess(apiResult.data.mapToPresenter())

                    is ApiResult.Error -> handleError(apiResult.throwable)
                }
            }
        }
    }

    private fun handleLoading() {
        _uiState.value = SignUpUiState(isLoading = true)
    }

    private fun handleSuccess(result: SignUp) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isRegistered = result.isSuccess,
                apiResultError = result.signUpErrorMsg,
            )
        }
    }

    private fun handleError(error: Throwable) {
        Logger.e("${error.message}")
        _uiState.update {
            it.copy(
                isLoading = false,
                isRegistered = false,
                apiResultError = "occur Error [request Login API]"
            )
        }
    }
}