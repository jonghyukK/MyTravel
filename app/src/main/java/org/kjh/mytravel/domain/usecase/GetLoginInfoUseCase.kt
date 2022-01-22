package org.kjh.mytravel.domain.usecase

import kotlinx.coroutines.flow.first
import org.kjh.mytravel.domain.repository.LoginPreferencesRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetLoginInfoUseCase
 * Created by jonghyukkang on 2022/01/23.
 *
 * Description:
 */
class GetLoginInfoUseCase @Inject constructor(
    private val loginPreferencesRepository: LoginPreferencesRepository
){
    suspend operator fun invoke() =
        loginPreferencesRepository.loginInfoPreferencesFlow.first().email

//    val loginInfoPreferencesFlow = loginPreferencesRepository.loginInfoPreferencesFlow
//
//    suspend fun fetchInitialPreferences() = loginPreferencesRepository.fetchInitialPreferences()
//
//    suspend fun updateLoginInfoPreferences(loginInfoPreferences: LoginInfoPreferences) =
//        loginPreferencesRepository.updateLoginInfoPreferences(loginInfoPreferences)
}