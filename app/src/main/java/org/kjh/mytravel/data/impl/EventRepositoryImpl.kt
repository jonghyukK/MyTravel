package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.model.EventModel
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.EventRepository
import org.kjh.mytravel.ui.uistate.tempEventItems
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: EventRepositoryImpl
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val responseToEventResult: (Result<EventModel>) -> Result<EventModel>
): EventRepository {
    override fun getEventItems(): Flow<Result<EventModel>> = flow{
        val response = EventModel(
            eventList = tempEventItems
        )

        val str = Result.Success(response)
        emit(responseToEventResult(str))
    }
}