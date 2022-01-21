package org.kjh.mytravel.domain.usecase

import org.kjh.mytravel.domain.repository.UserRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetUserInfoUseCase
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */
class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    fun execute(email: String) = userRepository.getUserInfo(email)
}