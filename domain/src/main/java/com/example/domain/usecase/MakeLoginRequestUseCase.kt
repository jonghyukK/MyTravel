package com.example.domain.usecase

import com.example.domain.entity.ApiResult
import com.example.domain.entity.LoginInfoPreferences
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * MyTravel
 * Class: MakeLoginRequestUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class MakeLoginRequestUseCase @Inject constructor(
    private val loginRepository             : LoginRepository,
    private val saveLogInPreferenceUseCase  : SaveLogInPreferenceUseCase,
    private val insertOrUpdateMyProfileUseCase: InsertOrUpdateMyProfileUseCase
){
    suspend operator fun invoke(email: String, pw: String) =
        loginRepository.makeRequestLogin(email, pw)
            .map {
                if (it is ApiResult.Success && it.data.result) {
                    insertOrUpdateMyProfileUseCase(it.data.data!!)
                    saveLogInPreferenceUseCase(LoginInfoPreferences(email, true))
                }
                it
            }
}