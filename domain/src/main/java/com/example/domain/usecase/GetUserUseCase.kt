package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: GetUserUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend fun getMyProfile(
        myEmail: String
    ) = userRepository.getMyProfile(myEmail)

    suspend operator fun invoke(
        myEmail    : String,
        targetEmail: String? = null
    ) = userRepository.getUser(myEmail, targetEmail)
}