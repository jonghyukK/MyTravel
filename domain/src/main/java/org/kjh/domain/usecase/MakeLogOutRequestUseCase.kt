package org.kjh.domain.usecase

import org.kjh.domain.repository.LoginPreferencesRepository

/**
 * MyTravel
 * Class: MakeLogOutWithDeleteProfileUseCase
 * Created by jonghyukkang on 2022/02/04.
 *
 * Description:
 */
class MakeLogOutRequestUseCase(
    private val loginPreferencesRepository: LoginPreferencesRepository
){
    suspend operator fun invoke() = loginPreferencesRepository.makeRequestLogOut()
}