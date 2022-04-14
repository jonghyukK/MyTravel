package org.kjh.mytravel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.repository.LoginPreferencesRepository
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.example.domain2.usecase.GetUserUseCase
import com.example.domain2.usecase.UpdateBookmarkUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.User
import org.kjh.mytravel.model.mapToPresenter
import javax.inject.Inject

/**
 * MyTravel
 * Class: MyProfileViewModel
 * Created by jonghyukkang on 2022/04/11.
 *
 * Description:
 */


fun List<Bookmark>.isBookmarkedPlace(placeName: String) =
    this.let { bookmarkList ->
        bookmarkList.find { it.placeName == placeName } != null
    }

fun List<Post>.updateBookmarkStateWithPosts(bookmarks: List<Bookmark>) =
    this.map { post ->
        post.copy(
            isBookmarked = bookmarks.find { it.placeName == post.placeName } != null
        )
    }



@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getLoginPreferenceUseCase: GetLoginPreferenceUseCase,
    private val loginPreferencesRepository: LoginPreferencesRepository,
    private val getUserUseCase: GetUserUseCase,
    private val updateBookmarkUseCase : UpdateBookmarkUseCase,
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError: MutableSharedFlow<String?> = MutableSharedFlow()
    val isError = _isError.asSharedFlow()

    private val _myProfileState: MutableStateFlow<User?> = MutableStateFlow(null)
    val myProfileState = _myProfileState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private fun showLoading() { _isLoading.value = true }
    private fun hideLoading() { _isLoading.value = false }


    init {
        viewModelScope.launch {
            loginPreferencesRepository.loginInfoPreferencesFlow
                .collect {
                    _isLoggedIn.value = it.isLoggedIn

                    if (it.isLoggedIn) {
                        getMyProfile()
                    } else {
                        _myProfileState.value = null
                    }
                }
        }
    }

    private fun getMyProfile() {
        viewModelScope.launch {
            getUserUseCase.getMyProfile(getLoginPreferenceUseCase().email)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading -> showLoading()
                        is ApiResult.Success -> {
                            hideLoading()
                            _myProfileState.value = apiResult.data.mapToPresenter()
                        }
                        is ApiResult.Error -> {
                            hideLoading()
                            _isError.emit(apiResult.throwable.message)
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

                        _myProfileState.value?.let { profileState ->
                            _myProfileState.update {
                                profileState.copy(
                                    posts = profileState.posts.updateBookmarkStateWithPosts(bookmarks),
                                    bookMarks = bookmarks
                                )
                            }
                        }
                    }
                    is ApiResult.Error -> {
                        hideLoading()
                        _isError.emit(apiResult.throwable.message)
                    }
                }
            }
        }
    }

    fun updateMyProfile(profileItem: User) {
        _myProfileState.value = profileItem
    }
}