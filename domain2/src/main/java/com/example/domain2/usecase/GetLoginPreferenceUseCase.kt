package com.example.domain2.usecase

import com.example.domain2.repository.LoginPreferencesRepository
import kotlinx.coroutines.flow.first

/**
 * MyTravel
 * Class: GetLoginPreferenceUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class GetLoginPreferenceUseCase(
    private val loginPreferencesRepository: LoginPreferencesRepository
){
    suspend operator fun invoke() = loginPreferencesRepository.loginInfoPreferencesFlow.first()
}