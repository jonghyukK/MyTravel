package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: MakeSignUpRequestUseCase
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
class MakeSignUpRequestUseCase(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(email: String, pw: String, nickName: String) =
        userRepository.requestSignUp(email, pw, nickName)
}
