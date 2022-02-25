package com.example.domain2.repository

import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: LoginRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface LoginRepository {
    suspend fun makeRequestLogin(email: String, pw: String): Flow<ApiResult<LoginEntity>>
}