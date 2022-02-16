package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.LoginPreferencesRepositoryImpl
import com.example.domain.entity.ApiResult
import com.example.domain.entity.Bookmark
import com.example.domain.entity.Post
import com.example.domain.entity.User
import com.example.domain.repository.LoginPreferencesRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetLoginPreferenceUseCase
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.UpdateBookMarkUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
    val isLoggedIn: Boolean = true
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase,
    private val updateBookMarkUseCase: UpdateBookMarkUseCase,
    private val loginPreferencesRepository: LoginPreferencesRepository
): ViewModel() {
    private val _uiState : MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        viewModelScope.launch {
            loginPreferencesRepository.loginInfoPreferencesFlow.collect {
                _uiState.value = ProfileUiState(isLoggedIn = it.isLoggedIn)
                if (it.isLoggedIn) {
                    getMyProfileData()
                }
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
                                    userItem  = result.data.data
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

    fun updateMyProfile(userItem: User) {
        _uiState.update {
            it.copy(
                userItem = userItem
            )
        }
    }

    fun updateMyBookmark(postItem: Post) {
        viewModelScope.launch {
            updateBookMarkUseCase(
                postId = postItem.postId
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        if (result.data.result) {
                            val updatedPosts = _uiState.value.userItem!!.posts.map { currentPost ->
                                currentPost.copy(
                                    isBookmarked = result.data.bookMarks.find { it.placeName == currentPost.placeName } != null
                                )
                            }

                            _uiState.update {
                                it.copy(
                                    userItem = it.userItem!!.copy(
                                        posts = updatedPosts,
                                        bookMarks = result.data.bookMarks
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}