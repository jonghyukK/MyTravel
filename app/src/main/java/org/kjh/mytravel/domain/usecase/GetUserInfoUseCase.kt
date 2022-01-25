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
    private val userRepository     : UserRepository,
    private val getLoginInfoUseCase: GetLoginInfoUseCase
){
    suspend fun updateUserProfile(
        filePath : String?,
        nickName : String?,
        introduce: String?
    ) = userRepository.updateUserInfo(filePath, getLoginInfoUseCase(), nickName, introduce)

    suspend operator fun invoke() = userRepository.getUserInfo(getLoginInfoUseCase())
}