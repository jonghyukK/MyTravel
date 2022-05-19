package org.kjh.mytravel.ui.features.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.usecase.GetLoginPreferenceUseCase
import org.kjh.domain.usecase.GetUserUseCase
import org.kjh.domain.usecase.MakeRequestFollowOrNotUseCase
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.Follow
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import org.kjh.mytravel.ui.GlobalErrorHandler
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.utils.updateBookmarkStateWithPosts

/**
 * MyTravel
 * Class: UserViewModel
 * Created by mac on 2022/01/15.
 *
 * Description:
 */

class UserViewModel @AssistedInject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val makeRequestFollowOrNotUseCase: MakeRequestFollowOrNotUseCase,
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase,
    private val globalErrorHandler: GlobalErrorHandler,
    @Assisted private val initUserEmail: String
): ViewModel() {

    data class UserUiState(
        val isLoading : Boolean = false,
        val userItem  : User? = null
    )

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()

    private val _followState: MutableStateFlow<UiState<Follow>> = MutableStateFlow(UiState.Init)
    val followState = _followState.asStateFlow()

    init {
        fetchUserByEmail()
    }

    private fun fetchUserByEmail() {
        viewModelScope.launch {
            getUserUseCase(
                myEmail     = getLoginPreferenceUseCase().email,
                targetEmail = initUserEmail
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading -> _uiState.update {
                        it.copy(isLoading = true)
                    }

                    is ApiResult.Success -> {
                        val result = apiResult.data.mapToPresenter()

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                userItem = result
                            )
                        }
                    }

                    is ApiResult.Error -> {
                        Logger.e(apiResult.throwable.localizedMessage!!)

                        val errorMsg = "occur Error [Fetch User API]"
                        globalErrorHandler.sendError(errorMsg)

                        _uiState.update {
                            it.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun requestUpdateFollowState() {
        viewModelScope.launch {
            makeRequestFollowOrNotUseCase(
                myEmail     = getLoginPreferenceUseCase().email,
                targetEmail = initUserEmail
            ).collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }

                        is ApiResult.Success -> {
                            val result = apiResult.data.mapToPresenter()

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    userItem  = result.targetProfile
                                )
                            }

                            _followState.value = UiState.Success(result)
                        }

                        is ApiResult.Error -> {
                            Logger.e(apiResult.throwable.localizedMessage!!)

                            val errorMsg = "occur Error [Request Follow API]"
                            globalErrorHandler.sendError(errorMsg)

                            _uiState.update {
                                it.copy(isLoading = false)
                            }
                        }
                    }
                }
        }
    }

    fun updatePostsWithBookmark(myBookmarks: List<Bookmark>) {
        _uiState.value.userItem?.let { user ->
            val updatedUser = user.copy(
                posts = user.posts.updateBookmarkStateWithPosts(myBookmarks)
            )

            _uiState.update {
                it.copy(userItem = updatedUser)
            }
        }
    }

    fun initFollowState() {
        _followState.value = UiState.Init
    }

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
}