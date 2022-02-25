package com.example.domain2.repository

import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.SignUpEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: SignUpRepository
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
interface SignUpRepository {
    suspend fun makeRequestSignUp(
        email: String,
        pw: String,
        nick: String
    ): Flow<ApiResult<SignUpEntity>>
}