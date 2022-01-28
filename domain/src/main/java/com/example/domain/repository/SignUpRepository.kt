package com.example.domain.repository

import com.example.domain.entity.ApiResult
import com.example.domain.entity.SignUp
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: SignUpRepository
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
interface SignUpRepository {
    suspend fun makeRequestSignUp(email: String, pw: String, nick: String): Flow<ApiResult<SignUp>>
}