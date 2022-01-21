package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.DataStoreManager
import org.kjh.mytravel.domain.Result
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
    val isLoading      : Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val dataStoreManager  : DataStoreManager
): ViewModel() {
    private val _uiState : MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase
                .execute(dataStoreManager.getMyEmail.first())
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.value = ProfileUiState(isLoading = true)
                        }
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
                                        introduce      = introduce
                                    )
                                }
                            }
                        }
                        is Result.Error -> {

                        }
                    }
                }
        }
    }
}