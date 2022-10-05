package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: GetMyProfileUseCase
 * Created by jonghyukkang on 2022/08/24.
 *
 * Description:
 */
class GetMyProfileUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.myProfileFlow
}