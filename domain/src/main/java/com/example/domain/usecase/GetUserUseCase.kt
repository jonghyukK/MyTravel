package com.example.domain.usecase

import com.example.domain.entity.ApiResult
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetUserUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(
        myEmail    : String,
        targetEmail: String? = null
    ) = userRepository.getUser(myEmail, targetEmail)
}