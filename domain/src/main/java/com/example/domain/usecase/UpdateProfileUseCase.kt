package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: UpdateProfileUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class UpdateProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(
        profileImg: String?,
        email: String,
        nickName: String,
        introduce: String?
    ) = userRepository.updateUserProfile(profileImg, email, nickName, introduce)
}