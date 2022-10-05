package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: DeleteMyUserUseCase
 * Created by jonghyukkang on 2022/08/22.
 *
 * Description:
 */
class DeleteMyProfileUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke() = userRepository.deleteMyProfile()
}