package org.kjh.mytravel.ui.features.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.domain.usecase.GetMyProfileUseCase
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.mapToPresenter
import javax.inject.Inject

/**
 * MyTravel
 * Class: BookmarkViewModel
 * Created by jonghyukkang on 2022/10/04.
 *
 * Description:
 */

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase,
    private val eventHandler: EventHandler
): ViewModel() {

    private val _uiState: MutableStateFlow<BookmarkUiState> = MutableStateFlow(BookmarkUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        viewModelScope.launch {
            getMyProfileUseCase()
                .map { userEntity ->
                    userEntity?.bookMarks?.map {
                        it.mapToPresenter()
                    } ?: emptyList()
                }
                .distinctUntilChanged()
                .collect { bookmarks ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            bookmarkItems = bookmarks.toUiState(
                                onBookmarkAction = { sendEventForUpdateBookmark(it) }
                            )
                        )
                    }
                }
        }
    }

    private fun sendEventForUpdateBookmark(placeName: String) {
        showLoading()
        viewModelScope.launch {
            eventHandler.emitEvent(Event.RequestUpdateBookmark(placeName))
        }
    }

    private fun showLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }
}

data class BookmarkUiState(
    val isLoading: Boolean = true,
    val bookmarkItems: List<BookmarkItemUiState> = emptyList()
)

data class BookmarkItemUiState(
    val placeName   : String,
    val placeImgUrl : String,
    val cityName    : String,
    val subCityName : String,
    val isBookmarked: Boolean,
    val onBookmark  : () -> Unit
)

private fun List<Bookmark>.toUiState(onBookmarkAction: (String) -> Unit) =
    map {
        with (it) {
            BookmarkItemUiState(
                placeName   = placeName,
                placeImgUrl = placeImg,
                cityName    = cityName,
                subCityName = subCityName,
                isBookmarked = isBookmarked,
                onBookmark  = { onBookmarkAction(placeName) }
            )
        }
    }
