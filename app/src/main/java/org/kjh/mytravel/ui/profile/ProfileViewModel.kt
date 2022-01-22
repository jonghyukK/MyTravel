package org.kjh.mytravel.ui.profile

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.data.model.Post
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.LoginPreferencesRepository
import org.kjh.mytravel.domain.usecase.GetUserInfoUseCase
import javax.inject.Inject

/**
 * MyTravel
 * Class: ProfileViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

data class ProfileUiState(
    val userId         : Int = 0,
    val email          : String = "",
    val nickName       : String = "",
    val profileImg     : String? = null,
    val postCount      : Int = 0,
    val followingCount : Int = 0,
    val followCount    : Int = 0,
    val introduce      : String? = null,
    val posts          : List<Post> = listOf(),
    val isLoading      : Boolean = false,
    val isLoggedIn     : Boolean = true,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val loginPrefRepository: LoginPreferencesRepository,
): ViewModel() {
    private val _uiState : MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        viewModelScope.launch {
            val isLoggedIn = loginPrefRepository.fetchInitialPreferences().isLoggedIn

            if (isLoggedIn) {
                getMyProfileData()
            } else {
                _uiState.value = ProfileUiState(isLoggedIn = false)
            }
        }
    }

    private fun getMyProfileData() {
        viewModelScope.launch {
            getUserInfoUseCase()
                .collect { result ->
                    when (result) {
                        is Result.Loading -> _uiState.value = ProfileUiState(isLoading = true)
                        is Result.Success -> {
                            with (result.data.userData) {
                                _uiState.update {
                                    it.copy(
                                        userId         = userId,
                                        email          = email,
                                        nickName       = nickName,
                                        profileImg     = profileImg,
                                        postCount      = postCount,
                                        followingCount = followingCount,
                                        followCount    = followCount,
                                        introduce      = introduce,
                                        posts          = posts
                                    )
                                }
                            }
                        }
                        is Result.Error -> {}
                    }
                }
        }
    }
}