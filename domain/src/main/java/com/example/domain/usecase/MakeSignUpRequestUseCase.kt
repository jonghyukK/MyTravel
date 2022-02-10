package com.example.domain.usecase

import com.example.domain.entity.ApiResult
import com.example.domain.entity.LoginInfoPreferences
import com.example.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * MyTravel
 * Class: MakeSignUpRequestUseCase
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
class MakeSignUpRequestUseCase @Inject constructor(
    private val repository: SignUpRepository,
    private val saveLogInPreferenceUseCase: SaveLogInPreferenceUseCase,
){
    suspend operator fun invoke(email: String, pw: String, nickName: String) =
        repository.makeRequestSignUp(email, pw, nickName)
            .map {
                if (it is ApiResult.Success && it.data.isRegistered) {
                    saveLogInPreferenceUseCase(LoginInfoPreferences(email, true))
                }
                it
            }
}
