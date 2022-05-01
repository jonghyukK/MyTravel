package org.kjh.domain.usecase

import kotlinx.coroutines.flow.map
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.LoginInfoPreferences
import org.kjh.domain.repository.SignUpRepository

/**
 * MyTravel
 * Class: MakeSignUpRequestUseCase
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
class MakeSignUpRequestUseCase(
    private val repository: SignUpRepository,
    private val saveLogInPreferenceUseCase: SaveLogInPreferenceUseCase,
){
    suspend operator fun invoke(email: String, pw: String, nickName: String) =
        repository.makeRequestSignUp(email, pw, nickName)
            .map {
                if (it is ApiResult.Success && it.data.isRegistered) {
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
