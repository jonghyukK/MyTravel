package org.kjh.mytravel.domain.usecase

import org.kjh.mytravel.domain.repository.EventRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetEventsUseCase
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
class GetEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
){
    fun execute() = eventRepository.getEventItems()
}