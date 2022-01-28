package com.example.domain.usecase

import com.example.domain.repository.LoginPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetLoginPreferenceUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class GetLoginPreferenceUseCase @Inject constructor(
    private val loginPreferencesRepository: LoginPreferencesRepository
){
    suspend operator fun invoke() = loginPreferencesRepository.loginInfoPreferencesFlow.first()
}