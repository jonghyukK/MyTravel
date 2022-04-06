package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.repository.LoginPreferencesRepository
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.GetUserUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import javax.inject.Inject

/**
 * MyTravel
 * Class: ProfileViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

//sealed class ProfileUiState {
//    object Loading  : ProfileUiState()
//    object NotLogin : ProfileUiState()
//    data class Success(val profileItem: User): ProfileUiState()
//    data class Error(val error: Throwable?): ProfileUiState()
//}

data class ProfileUiState(
    val isLoading  : Boolean = false,
    val profileItem: User? = null,
    val isError    : String? = null
)

sealed class LoginState {
    object Init: LoginState()
    object LoggedIn : LoginState()
    object NotLogIn : LoginState()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase,
    private val loginPreferencesRepository: LoginPreferencesRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Init)
    val loginState = _loginState.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        viewModelScope.launch {
            loginPreferencesRepository.loginInfoPreferencesFlow
                .collect {
                    _loginState.value = if (it.isLoggedIn) LoginState.LoggedIn else LoginState.NotLogIn

                    if (it.isLoggedIn)
                        getMyProfileData()
                }
        }
    }

    private fun getMyProfileData() {
        viewModelScope.launch {
            getUserUseCase.getMyProfile(getLoginPreferenceUseCase().email)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _uiState.value = ProfileUiState(isLoading = true)

                        is ApiResult.Success ->
                            _uiState.update {
                                it.copy(
                                    isLoading   = false,
                                    profileItem = apiResult.data.mapToPresenter()
                                )
                            }

                        is ApiResult.Error ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isError   = apiResult.throwable.localizedMessage
                                )
                            }
                    }
                }
        }
    }

    fun shownErrorToast() {
        _uiState.update {
            it.copy(
                isError = null
            )
        }
    }

    fun updatePostBookmarkState(bookmarks: List<Bookmark>) {
        _uiState.value.profileItem?.let { profileItem ->
            if (profileItem.posts.isNotEmpty()) {
                _uiState.update { currentUiState ->
                    currentUiState.copy(
                        profileItem = currentUiState.profileItem!!.copy(
                            posts = currentUiState.profileItem.posts.map { post ->
                                post.copy(
                                    isBookmarked = bookmarks.find { it.placeName == post.placeName } != null
                                )
                            }
                        )
                    )
                }
            }
        }
    }

    fun updateProfileItem(profileItem: User) {
        _uiState.update {
            it.copy(profileItem = profileItem)
        }
    }
}