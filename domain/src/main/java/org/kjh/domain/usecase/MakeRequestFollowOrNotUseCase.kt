package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: MakeRequestFollowOrNotUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class MakeRequestFollowOrNotUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(myEmail: String, targetEmail: String) =
        userRepository.updateFollowState(myEmail, targetEmail)
}