package com.example.domain2.usecase

import com.example.domain2.repository.LoginPreferencesRepository

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