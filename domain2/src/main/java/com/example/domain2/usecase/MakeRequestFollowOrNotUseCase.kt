package com.example.domain2.usecase

import com.example.domain2.repository.UserRepository

/**
 * MyTravel
 * Class: MakeRequestFollowOrNotUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class MakeRequestFollowOrNotUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(myEmail: String, targetEmail: String) =
        userRepository.requestFollowOrUnFollow(myEmail, targetEmail)
}