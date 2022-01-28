package com.example.domain.repository

import com.example.domain.entity.ApiResult
import com.example.domain.entity.Login
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: LoginRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface LoginRepository {
    suspend fun makeRequestLogin(email: String, pw: String): Flow<ApiResult<Login>>
}