package org.kjh.mytravel.ui.profile

import androidx.lifecycle.*
import com.example.domain.entity.ApiResult
import com.example.domain.entity.User
import com.example.domain.usecase.GetLoginPreferenceUseCase
import com.example.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
            val isLoggedIn = getLoginPreferenceUseCase().isLoggedIn

            if (isLoggedIn) {
                getMyProfileData()
            } else {
                _uiState.value = ProfileUiState(isLoggedIn = false)
            }
        }
    }

    private fun getMyProfileData() {
        viewModelScope.launch {
            getUserUseCase(getLoginPreferenceUseCase().email)
                .collect { result ->
                    when (result) {
                        is ApiResult.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }
                        is ApiResult.Success -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                userItem  = result.data
                            )
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