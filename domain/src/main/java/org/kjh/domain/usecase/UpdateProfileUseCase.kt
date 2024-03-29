package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: UpdateProfileUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class UpdateProfileUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        profileImg: String?,
        nickName  : String,
        introduce : String?
    ) = userRepository.updateMyProfile(profileImg, nickName, introduce)
}