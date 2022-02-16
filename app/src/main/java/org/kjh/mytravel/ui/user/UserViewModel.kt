package org.kjh.mytravel.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.ApiResult
import com.example.domain.entity.Bookmark
import com.example.domain.entity.Post
import com.example.domain.entity.User
import com.example.domain.usecase.GetLoginPreferenceUseCase
import com.example.domain.usecase.GetUserUseCase
import com.example.domain.usecase.MakeRequestFollowOrNotUseCase
import com.example.domain.usecase.UpdateBookMarkUseCase
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * MyTravel
 * Class: UserViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

data class UserUiState(
    val userItem  : User? = null,
    val isLoading : Boolean = false,
    val successFollowOrNot : Boolean = false
)

class UserViewModel @AssistedInject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val makeRequestFollowOrNotUseCase: MakeRequestFollowOrNotUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase,
    @Assisted private val initUserEmail: String
): ViewModel() {

    @AssistedFactory
    interface UserNameAssistedFactory {
        fun create(userName: String): UserViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: UserNameAssistedFactory,
            userName: String
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>) =
                assistedFactory.create(userName) as T
        }
    }

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState : StateFlow<UserUiState> = _uiState

    init {
        viewModelScope.launch {
            getUserUseCase(
                myEmail     = getLoginPreferenceUseCase().email,
                targetEmail = initUserEmail
            ).collect { result ->
                    when (result) {
                        is ApiResult.Loading -> _uiState.value = UserUiState(isLoading = true)
                        is ApiResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    userItem = result.data.data
                                )
                            }
                        }
                        is ApiResult.Error -> {}
                    }
                }
        }
    }

    fun makeRequestFollow(targetEmail: String) {
        viewModelScope.launch {
            makeRequestFollowOrNotUseCase(
                myEmail     = getLoginPreferenceUseCase().email,
                targetEmail = targetEmail
            ).collect { result ->
                    when (result) {
                        is ApiResult.Loading ->
                            _uiState.update {
                                it.copy(isLoading = true, successFollowOrNot = false)
                            }

                        is ApiResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    userItem = it.userItem?.copy(
                                        followCount = result.data.data.followCount,
                                        isFollowing = result.data.data.isFollowing
                                    ),
                                    successFollowOrNot = result.data.result
                                )
                            }
                        }
                        is ApiResult.Error -> {
                            Logger.e(result.throwable.localizedMessage)
                        }
                    }
                }
        }
    }

    fun sentRequestUpdateMyFollowCount() {
        _uiState.update { currentUiState ->
            currentUiState.copy(successFollowOrNot = false)
        }
    }

    fun updateUserPostBookmarkState(bookmarks: List<Post>) {
        _uiState.value.userItem?.let {
            _uiState.update { currentUiState ->
                currentUiState.copy(
                    userItem = currentUiState.userItem!!.copy(
                        posts = currentUiState.userItem.posts.map { currentPost ->
                            currentPost.copy(isBookmarked = bookmarks.find { it.placeName == currentPost.placeName } != null)
                        }
                    )
                )
            }
        }
    }
}