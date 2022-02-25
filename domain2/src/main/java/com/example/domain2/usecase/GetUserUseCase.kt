package com.example.domain2.usecase

import com.example.domain2.repository.UserRepository

/**
 * MyTravel
 * Class: GetUserUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

class GetUserUseCase(
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