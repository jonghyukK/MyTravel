package org.kjh.domain.usecase

import org.kjh.domain.entity.LoginInfoPreferences
import org.kjh.domain.repository.LoginPreferencesRepository

/**
 * MyTravel
 * Class: SaveLogInPreferenceUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

class SaveLogInPreferenceUseCase(
    private val loginPreferencesRepository: LoginPreferencesRepository
){
    suspend operator fun invoke(loginInfoPreferences: LoginInfoPreferences) =
        loginPreferencesRepository.updateLoginInfoPreferences(loginInfoPreferences)
}