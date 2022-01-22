package org.kjh.mytravel.domain.usecase

import kotlinx.coroutines.flow.map
import org.kjh.mytravel.data.impl.LoginInfoPreferences
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.LoginPreferencesRepository
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
    private val loginRepository    : LoginRepository,
    private val loginPrefRepository: LoginPreferencesRepository
) {
    suspend operator fun invoke(email: String, pw: String) =
        loginRepository.makeRequestLogin(email, pw)
            .map {
                if (it is Result.Success && it.data.isLoggedIn) {
                    loginPrefRepository.updateLoginInfoPreferences(
                        LoginInfoPreferences(email, true)
                    )
                }
                it
            }
}