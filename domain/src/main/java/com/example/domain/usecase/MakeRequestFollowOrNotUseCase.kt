package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: MakeRequestFollowOrNotUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class MakeRequestFollowOrNotUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(myEmail: String, targetEmail: String) =
        userRepository.requestFollowOrUnFollow(myEmail, targetEmail)
}