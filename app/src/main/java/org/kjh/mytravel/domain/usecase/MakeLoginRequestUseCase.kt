package org.kjh.mytravel.domain.usecase

import org.kjh.mytravel.domain.repository.LoginRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: MakeLoginRequestUseCase
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
class MakeLoginRequestUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    fun execute(email: String, pw: String) =
        loginRepository.makeRequestLogin(email, pw)
}