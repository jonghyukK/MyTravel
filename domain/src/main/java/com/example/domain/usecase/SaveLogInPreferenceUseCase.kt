package com.example.domain.usecase

import com.example.domain.entity.LoginInfoPreferences
import com.example.domain.repository.LoginPreferencesRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: SaveLogInPreferenceUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

class SaveLogInPreferenceUseCase @Inject constructor(
    private val loginPreferencesRepository: LoginPreferencesRepository
){
    suspend operator fun invoke(loginInfoPreferences: LoginInfoPreferences) =
        loginPreferencesRepository.updateLoginInfoPreferences(loginInfoPreferences)
}