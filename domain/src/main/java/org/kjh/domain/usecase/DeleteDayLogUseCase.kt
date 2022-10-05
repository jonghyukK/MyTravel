package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: DeleteDayLogUseCase
 * Created by jonghyukkang on 2022/08/18.
 *
 * Description:
 */
class DeleteDayLogUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(dayLogId: Int) = userRepository.deleteMyDayLog(dayLogId)
}