package com.example.domain2.usecase

import com.example.domain2.repository.SignUpRepository
import kotlinx.coroutines.flow.map

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
                if (it is com.example.domain2.entity.ApiResult.Success && it.data.isRegistered) {
                    saveLogInPreferenceUseCase(
                        com.example.domain2.entity.LoginInfoPreferences(
                            email,
                            true
                        )
                    )
                }
                it
            }
}
