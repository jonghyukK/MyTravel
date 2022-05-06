package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

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
    ) = userRepository.fetchMyProfile(myEmail)

    suspend operator fun invoke(
        myEmail    : String,
        targetEmail: String? = null
    ) = userRepository.fetchUser(myEmail, targetEmail)
}