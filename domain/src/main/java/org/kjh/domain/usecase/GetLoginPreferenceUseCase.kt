package org.kjh.domain.usecase

import kotlinx.coroutines.flow.first
import org.kjh.domain.repository.LoginPreferencesRepository

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