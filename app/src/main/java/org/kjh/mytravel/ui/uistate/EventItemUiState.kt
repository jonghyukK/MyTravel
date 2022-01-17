package org.kjh.mytravel.ui.uistate

import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * MyTravel
 * Class: EventItemUiState
 * Created by mac on 2022/01/14.
 *
 * Description:
 */
data class EventItemUiState(
    val eventId     : Long,
    val eventTitle  : String,
    val placeItems  : List<PlaceItemUiState> = listOf()
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<EventItemUiState>() {
            override fun areItemsTheSame(
                oldItem: EventItemUiState,
                newItem: EventItemUiState
            ): Boolean =
                oldItem.eventId == newItem.eventId

            override fun areContentsTheSame(
                oldItem: EventItemUiState,
                newItem: EventItemUiState
            ): Boolean =
                oldItem == newItem
        }
    }
}

val tempEventItems = listOf(
    EventItemUiState(
        eventId = 1111,
        eventTitle = "서울, 여기 가보는 건 어때요?",
        placeItems = tempPlaceItemList
    ),
    EventItemUiState(
        eventId = 2222,
        eventTitle = "인천의 핫한 곳은 여기!!",
        placeItems = tempPlaceItemList2
    )
)

val tempEventItemsFlow: Flow<List<EventItemUiState>> = flow {
    emit(tempEventItems)
}
