package org.kjh.mytravel.domain.usecase

import kotlinx.coroutines.flow.map
import org.kjh.mytravel.data.impl.LoginInfoPreferences
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.LoginPreferencesRepository
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
    private val signUpRepository   : SignUpRepository,
    private val loginPrefRepository: LoginPreferencesRepository
) {
    suspend operator fun invoke(email: String, pw: String, nick: String) =
        signUpRepository.makeRequestSignUp(email, pw, nick)
            .map {
                if (it is Result.Success && it.data.isRegistered) {
                    loginPrefRepository.updateLoginInfoPreferences(
                        LoginInfoPreferences(email, true)
                    )
                }
                it
            }
}