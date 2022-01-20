package org.kjh.mytravel.domain.usecase

import org.kjh.mytravel.domain.repository.SignUpRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: makeSignUpRequestUseCase
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
class MakeSignUpRequestUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    fun execute(email: String, pw: String, nick: String) =
        signUpRepository.makeRequestSignUp(email, pw, nick)
}