package org.kjh.domain.usecase

import kotlinx.coroutines.flow.map
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.LoginInfoPreferences
import org.kjh.domain.repository.LoginRepository

/**
 * MyTravel
 * Class: MakeLoginRequestUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class MakeLoginRequestUseCase(
    private val loginRepository             : LoginRepository,
    private val saveLogInPreferenceUseCase  : SaveLogInPreferenceUseCase
){
    suspend operator fun invoke(email: String, pw: String) =
        loginRepository.makeRequestLogin(email, pw)
            .map {
                if (it is ApiResult.Success && it.data.isLoggedIn) {
                    saveLogInPreferenceUseCase(
                        LoginInfoPreferences(
                            email,
                            true
                        )
                    )
                }
                it
            }
}