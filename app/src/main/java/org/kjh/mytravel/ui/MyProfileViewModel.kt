package org.kjh.mytravel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.repository.LoginPreferencesRepository
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.GetUserUseCase
import com.example.domain2.usecase.UpdateBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.*
import org.kjh.mytravel.ui.common.ErrorMsg
import javax.inject.Inject

/**
 * MyTravel
 * Class: MyProfileViewModel
 * Created by jonghyukkang on 2022/04/11.
 *
 * Description:
 */


data class MyProfileUiState(
    val myProfileItem   : User? = null,
    val myPostItems     : List<Post> = emptyList(),
    val myBookmarkItems : List<Bookmark> = emptyList()
)

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getLoginPreferenceUseCase : GetLoginPreferenceUseCase,
    private val loginPreferencesRepository: LoginPreferencesRepository,
    private val getUserUseCase            : GetUserUseCase,
    private val updateBookmarkUseCase     : UpdateBookmarkUseCase,
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _myProfileState = MutableStateFlow(MyProfileUiState())
    val myProfileState = _myProfileState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _isError: MutableSharedFlow<ErrorMsg> = MutableSharedFlow()
    val isError = _isError.asSharedFlow()

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
                        getMyProfile()
                    } else {
                        _myProfileState.value = MyProfileUiState()
                    }
                }
        }
    }

    // API - My Profile.
    private fun getMyProfile() {
        viewModelScope.launch {
            getUserUseCase.getMyProfile(getLoginPreferenceUseCase().email)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> showLoading()

                        is ApiResult.Success -> {
                            hideLoading()
                            val result = apiResult.data.mapToPresenter()

                            _myProfileState.value = MyProfileUiState(
                                myProfileItem   = result,
                                myPostItems     = result.posts,
                                myBookmarkItems = result.bookMarks
                            )
                        }

                        is ApiResult.Error -> {
                            hideLoading()
                            _isError.emit(ErrorMsg.ERROR_MY_PROFILE)
                        }
                    }
                }
        }
    }

    // API - Update Bookmark State of Post.
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
                        _isError.emit(ErrorMsg.ERROR_UPDATE_BOOKMARK)
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