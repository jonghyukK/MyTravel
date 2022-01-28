package com.example.domain.usecase

import com.example.domain.entity.ApiResult
import com.example.domain.entity.Login
import com.example.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: MakeLoginRequestUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class MakeLoginRequestUseCase @Inject constructor(
    private val loginRepository: LoginRepository
){
    suspend operator fun invoke(email: String, pw: String) =
        loginRepository.makeRequestLogin(email, pw)
}