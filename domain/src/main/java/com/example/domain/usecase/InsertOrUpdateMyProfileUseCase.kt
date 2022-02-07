package com.example.domain.usecase

import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: InsertOrUpdateMyProfileUseCase
 * Created by jonghyukkang on 2022/02/07.
 *
 * Description:
 */
class InsertOrUpdateMyProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(user: User) = userRepository.insertOrUpdateMyProfile(user)
}