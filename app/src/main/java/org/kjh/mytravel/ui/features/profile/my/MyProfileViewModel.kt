package org.kjh.mytravel.ui.features.profile.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.repository.LoginPreferencesRepository
import org.kjh.domain.usecase.GetLoginPreferenceUseCase
import org.kjh.domain.usecase.GetUserUseCase
import org.kjh.domain.usecase.UpdateBookmarkUseCase
import org.kjh.mytravel.model.*
import org.kjh.mytravel.ui.GlobalErrorHandler
import org.kjh.mytravel.utils.updateBookmarkStateWithPosts
import javax.inject.Inject

/**
 * MyTravel
 * Class: MyProfileViewModel
 * Created by jonghyukkang on 2022/04/11.
 *
 * Description:
 */

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getLoginPreferenceUseCase : GetLoginPreferenceUseCase,
    private val loginPreferencesRepository: LoginPreferencesRepository,
    private val getUserUseCase            : GetUserUseCase,
    private val updateBookmarkUseCase     : UpdateBookmarkUseCase,
    private val globalErrorHandler        : GlobalErrorHandler
): ViewModel() {

    data class MyProfileUiState(
        val myProfileItem   : User? = null,
        val myPostItems     : List<Post> = emptyList(),
        val myBookmarkItems : List<Bookmark> = emptyList()
    )

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _myProfileUiState = MutableStateFlow(MyProfileUiState())
    val myProfileUiState = _myProfileUiState.asStateFlow()

    private val _isNotLogIn: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isNotLogIn = _isNotLogIn.asStateFlow()

    private val _motionProgress = MutableStateFlow(0f)
    val motionProgress = _motionProgress.asStateFlow()

    private fun showLoading() { _isLoading.value = true }
    private fun hideLoading() { _isLoading.value = false }

    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch {
            loginPreferencesRepository.loginInfoPreferencesFlow
                .collect {
                    _isNotLogIn.value = !it.isLoggedIn

                    if (it.isLoggedIn) {
                        fetchMyProfile()
                    } else {
                        _myProfileUiState.value = MyProfileUiState()
                    }
                }
        }
    }

    private fun fetchMyProfile() {
        viewModelScope.launch {
            getUserUseCase.getMyProfile(getLoginPreferenceUseCase().email)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> showLoading()

                        is ApiResult.Success -> {
                            hideLoading()
                            val result = apiResult.data.mapToPresenter()

                            _myProfileUiState.value = MyProfileUiState(
                                myProfileItem   = result,
                                myPostItems     = result.posts,
                                myBookmarkItems = result.bookMarks
                            )
                        }

                        is ApiResult.Error -> {
                            hideLoading()
                            Logger.e(apiResult.throwable.localizedMessage!!)

                            val errorMsg = "occur Error [Fetch My Profile API]"
                            globalErrorHandler.sendError(errorMsg)
                        }
                    }
                }
        }
    }

    fun updateBookmark(postId: Int, placeName: String) {
        viewModelScope.launch {
            updateBookmarkUseCase(
                myEmail   = getLoginPreferenceUseCase().email,
                postId    = postId,
                placeName = placeName
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading -> showLoading()

                    is ApiResult.Success -> {
                        hideLoading()

                        val bookmarks = apiResult.data.map { it.mapToPresenter() }

                        _myProfileUiState.update { profileState ->
                            profileState.copy(
                                myBookmarkItems = bookmarks,
                                myPostItems     = profileState.myPostItems.updateBookmarkStateWithPosts(bookmarks)
                            )
                        }
                    }

                    is ApiResult.Error -> {
                        hideLoading()
                        Logger.e(apiResult.throwable.localizedMessage!!)

                        val errorMsg = "occur Error [Update Bookmark API]"
                        globalErrorHandler.sendError(errorMsg)
                    }
                }
            }
        }
    }

    fun updateMyProfile(profileItem: User) {
        _myProfileUiState.update { profileState ->
            profileState.copy(
                myProfileItem   = profileItem,
                myBookmarkItems = profileItem.bookMarks,
                myPostItems     = profileItem.posts
            )
        }
    }

    fun saveMotionProgress(value: Float) {
        _motionProgress.value = value
    }
}