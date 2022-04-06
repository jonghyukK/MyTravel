package org.kjh.mytravel.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain2.entity.ApiResult
import com.example.domain2.usecase.GetBookmarkUseCase
import com.example.domain2.usecase.UpdateBookmarkUseCase
import com.example.domain2.usecase.GetLoginPreferenceUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.mapToPresenter
import javax.inject.Inject

/**
 * MyTravel
 * Class: BookMarkViewModel
 * Created by jonghyukkang on 2022/02/11.
 *
 * Description:
 */

data class BookmarkUiState(
    val isLoading: Boolean = false,
    val isEmpty  : Boolean = false,
    val bookmarkItems: List<Bookmark> = listOf(),
    val isError  : String? = null
)

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val updateBookmarkUseCase : UpdateBookmarkUseCase,
    private val getBookmarkUseCase    : GetBookmarkUseCase,
    private val loginPreferenceUseCase: GetLoginPreferenceUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<BookmarkUiState> = MutableStateFlow(BookmarkUiState())
    val uiState: StateFlow<BookmarkUiState> = _uiState

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        viewModelScope.launch {
            getBookmarkUseCase(loginPreferenceUseCase().email)
                .collect { apiResult ->
                    when (apiResult) {
                        is ApiResult.Loading ->
                            _uiState.value = BookmarkUiState(isLoading = true)

                        is ApiResult.Success -> {
                            val result = apiResult.data.map { it.mapToPresenter() }

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isEmpty   = result.isEmpty(),
                                    bookmarkItems = result
                                )
                            }
                        }

                        is ApiResult.Error ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isError = apiResult.throwable.localizedMessage
                                )
                            }
                    }
                }
        }
    }

    fun updateBookmark(postId: Int, placeName: String) {
        viewModelScope.launch {
            updateBookmarkUseCase(
                myEmail   = loginPreferenceUseCase().email,
                postId    = postId,
                placeName = placeName
            ).collect { apiResult ->
                when (apiResult) {
                    is ApiResult.Loading ->
                        _uiState.update {
                            it.copy(isLoading = true)
                        }

                    is ApiResult.Success -> {
                        val result = apiResult.data.map { it.mapToPresenter() }

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isEmpty   = result.isEmpty(),
                                bookmarkItems = result
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

    fun shownErrorToast() {
        _uiState.update {
            it.copy(isError = null)
        }
    }
}