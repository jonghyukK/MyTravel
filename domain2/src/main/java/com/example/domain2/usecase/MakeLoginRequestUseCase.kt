package com.example.domain2.usecase

import com.example.domain2.repository.LoginRepository
import kotlinx.coroutines.flow.map

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
                if (it is com.example.domain2.entity.ApiResult.Success && it.data.isLoggedIn) {
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