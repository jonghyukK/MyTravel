package org.kjh.mytravel.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.GetUserUseCase
import com.example.domain2.usecase.MakeRequestFollowOrNotUseCase
import com.example.domain2.usecase.UpdateBookmarkUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Follow
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter

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
    val isError   : String? = null
)

sealed class FollowState {
    object Init : FollowState()
    object Loading: FollowState()
    data class Success(val followItem: Follow): FollowState()
    data class Error(val error: String?): FollowState()
}

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

    private val _followState: MutableStateFlow<FollowState> = MutableStateFlow(FollowState.Init)
    val followState = _followState.asStateFlow()

    init {
        viewModelScope.launch {
            getUserUseCase(
                myEmail     = getLoginPreferenceUseCase().email,
                targetEmail = initUserEmail
            ).collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _uiState.update {
                                it.copy(
                                    isLoading = true
                                )
                            }

                        is ApiResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    userItem  = apiResult.data.mapToPresenter()
                                )
                            }
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

    fun makeRequestFollow(targetEmail: String) {
        viewModelScope.launch {
            makeRequestFollowOrNotUseCase(
                myEmail     = getLoginPreferenceUseCase().email,
                targetEmail = targetEmail
            ).collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> _followState.value = FollowState.Loading

                        is ApiResult.Success -> {
                            val targetProfile = apiResult.data.targetProfile.mapToPresenter()

                            _uiState.update {
                                it.copy(userItem = targetProfile)
                            }

                            _followState.value = FollowState.Success(Follow(
                                apiResult.data.myProfile.mapToPresenter(),
                                targetProfile
                            ))
                        }

                        is ApiResult.Error ->
                            _followState.value = FollowState.Error(apiResult.throwable.localizedMessage)
                    }
                }
        }
    }

    fun initFollowState() {
        _followState.value = FollowState.Init
    }

    fun shownErrorToast() {
        _uiState.update {
            it.copy(isError = null)
        }
    }
}