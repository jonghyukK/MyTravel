package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.ApiResult
import com.example.domain.entity.User
import com.example.domain.usecase.GetLoginPreferenceUseCase
import com.example.domain.usecase.GetUserUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * MyTravel
 * Class: ProfileViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

data class ProfileUiState(
    val userItem  : User? = null,
    val isLoading : Boolean = false,
    val isLoggedIn: Boolean = true,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {
    private val _uiState : MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        viewModelScope.launch {
            if (getLoginPreferenceUseCase().isLoggedIn) {
                Logger.d("isLoggedIn : ${getLoginPreferenceUseCase()}")
                getMyProfileData()
            } else {
                Logger.d("isLoggedIn : ${getLoginPreferenceUseCase()}")
                _uiState.value = ProfileUiState(isLoggedIn = false)
            }
        }
    }

    private fun getMyProfileData() {
        viewModelScope.launch {
            getUserUseCase.getMyProfile(getLoginPreferenceUseCase().email)
                .collect { result ->
                    when (result) {
                        is ApiResult.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }
                        is ApiResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    userItem  = result.data
                                )
                            }
                        }
                        is ApiResult.Error -> _uiState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }
}