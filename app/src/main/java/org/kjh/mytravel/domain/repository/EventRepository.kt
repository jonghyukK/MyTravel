package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.EventModel
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: EventRepository
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
interface EventRepository {

    fun getEventItems(): Flow<Result<EventModel>>
}