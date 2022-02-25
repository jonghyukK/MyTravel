package com.example.domain2.usecase

import com.example.domain2.entity.LoginInfoPreferences
import com.example.domain2.repository.LoginPreferencesRepository

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