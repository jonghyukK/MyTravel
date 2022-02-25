package org.kjh.mytravel.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.repository.LoginPreferencesRepository
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.GetUserUseCase
import com.example.domain2.usecase.UpdateBookMarkUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.Post
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
                                    userItem  = result.data.mapToPresenter()
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

    fun updateMyBookmark(postId: Int) {
        viewModelScope.launch {
            updateBookMarkUseCase(
                postId = postId
            ).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val newBookmarks = result.data.map { it.mapToPresenter() }

                        _uiState.value.userItem?.let { user ->
                            val updatedPosts = user.posts.map { currentPost ->
                                currentPost.copy(
                                    isBookmarked = newBookmarks.find { it.placeName == currentPost.placeName } != null
                                )
                            }

                            Logger.d("${updatedPosts}")

                            _uiState.update {
                                it.copy(
                                    userItem = user.copy(
                                        posts = updatedPosts,
                                        bookMarks = newBookmarks
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