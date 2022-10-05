package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: MakeLoginRequestUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class MakeLoginRequestUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, pw: String) =
        userRepository.requestLogin(email, pw)
}