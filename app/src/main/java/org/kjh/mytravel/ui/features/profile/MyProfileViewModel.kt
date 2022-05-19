package org.kjh.mytravel.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.repository.LoginPreferencesRepository
import org.kjh.domain.usecase.GetLoginPreferenceUseCase
import org.kjh.domain.usecase.GetUserUseCase
import org.kjh.domain.usecase.UpdateBookmarkUseCase
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
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

    data class MyProfileState(
        val myProfileItem   : User? = null,
        val myPostItems     : List<Post> = emptyList(),
        val myBookmarkItems : List<Bookmark> = emptyList()
    )

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _myProfileState = MutableStateFlow(MyProfileState())
    val myProfileState = _myProfileState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private fun showLoading() { _isLoading.value = true }
    private fun hideLoading() { _isLoading.value = false }

    init {
        checkLogin()
    }

    private fun checkLogin() {
        viewModelScope.launch {
            loginPreferencesRepository.loginInfoPreferencesFlow
                .collect {
                    _isLoggedIn.value = it.isLoggedIn

                    if (it.isLoggedIn) {
                        fetchMyProfile()
                    } else {
                        _myProfileState.value = MyProfileState()
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

                            _myProfileState.value = MyProfileState(
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

                        _myProfileState.update { profileState ->
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
        _myProfileState.update { profileState ->
            profileState.copy(
                myProfileItem   = profileItem,
                myBookmarkItems = profileItem.bookMarks,
                myPostItems     = profileItem.posts
            )
        }
    }
}