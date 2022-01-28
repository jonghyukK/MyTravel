package com.example.domain.usecase

import com.example.domain.entity.ApiResult
import com.example.domain.entity.SignUp
import com.example.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: MakeSignUpRequestUseCase
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
class MakeSignUpRequestUseCase @Inject constructor(
    private val repository: SignUpRepository
){
    suspend operator fun invoke(email: String, pw: String, nickName: String) =
        repository.makeRequestSignUp(email, pw, nickName)
}
