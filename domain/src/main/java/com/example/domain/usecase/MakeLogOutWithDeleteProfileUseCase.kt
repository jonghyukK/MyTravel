package com.example.domain.usecase

import com.example.domain.repository.LoginPreferencesRepository
import com.example.domain.repository.UserRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: MakeLogOutWithDeleteProfileUseCase
 * Created by jonghyukkang on 2022/02/04.
 *
 * Description:
 */
class MakeLogOutWithDeleteProfileUseCase @Inject constructor(
    private val loginPreferencesRepository: LoginPreferencesRepository,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(email: String) {
        loginPreferencesRepository.makeRequestLogOut()
        userRepository.deleteUser(email)
    }
}