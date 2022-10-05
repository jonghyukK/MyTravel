package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: GetUserUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

class GetUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(targetEmail: String) = userRepository.fetchUser(targetEmail)
}