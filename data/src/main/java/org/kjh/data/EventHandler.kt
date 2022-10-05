package org.kjh.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.kjh.data.model.BookmarkModel
import org.kjh.data.model.UserModel

/**
 * MyTravel
 * Class: EventHandler
 * Created by jonghyukkang on 2022/08/23.
 *
 * Description:
 */
class EventHandler {
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    suspend fun emitEvent(event: Event) {
        _event.emit(event)
    }
}

sealed class Event {
    object LoginEvent: Event()
    object LogoutEvent: Event()
    object ShowLoginDialogEvent: Event()

    data class ApiError(val errorMsg: String): Event()
    data class DeleteDayLog(val dayLogId: Int): Event()
    data class UpdateBookmark(
        val bookmarks: List<BookmarkModel>,
        val placeName: String
    ): Event()
    data class RequestUpdateBookmark(val placeName: String): Event()
    data class RequestDeleteDayLog(val dayLogId: Int): Event()
}