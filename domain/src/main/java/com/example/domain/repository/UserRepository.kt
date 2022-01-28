package com.example.domain.repository

import com.example.domain.entity.ApiResult
import com.example.domain.entity.UpdateProfile
import com.example.domain.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: UserRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface UserRepository {
    suspend fun getUser(
        myEmail    : String,
        targetEmail: String? = null
    ): Flow<ApiResult<User>>

    suspend fun updateUserProfile(
        profileUrl  : String?,
        email       : String,
        nickName    : String,
        introduce   : String?
    ): Flow<ApiResult<UpdateProfile>>
}