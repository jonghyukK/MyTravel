package org.kjh.mytravel.ui.features.signup

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
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
    private val makeSignUpRequestUseCase: MakeSignUpRequestUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState.Init)
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
                        _uiState.value = SignUpUiState.Loading

                    is ApiResult.Success ->
                        _uiState.value = SignUpUiState.Success

                    is ApiResult.Error -> {
                        val errorMsg = apiResult.throwable.message ?: "Unknown Error"
                        Logger.e(errorMsg)

                        _uiState.value = SignUpUiState.Failure(errorMsg)
                    }
                }
            }
        }
    }
}

sealed class SignUpUiState {
    object Init: SignUpUiState()
    object Loading: SignUpUiState()
    object Success: SignUpUiState()
    data class Failure(val errorMsg: String): SignUpUiState()

    fun isLoading() = this is Loading
    fun isFailure() = if (this is Failure) this.errorMsg else null
}