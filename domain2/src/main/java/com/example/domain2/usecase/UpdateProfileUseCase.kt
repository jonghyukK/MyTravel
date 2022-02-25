package com.example.domain2.usecase

import com.example.domain2.repository.UserRepository

/**
 * MyTravel
 * Class: UpdateProfileUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class UpdateProfileUseCase(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(
        profileImg: String?,
        email: String,
        nickName: String,
        introduce: String?
    ) = userRepository.updateUserProfile(profileImg, email, nickName, introduce)
}