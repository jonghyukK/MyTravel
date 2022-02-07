package com.example.domain.repository

import com.example.domain.entity.ApiResult
import com.example.domain.entity.UpdateProfile
import com.example.domain.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: UserRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface UserRepository {

    suspend fun getMyProfile(
        myEmail: String
    ): Flow<ApiResult<User>>

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

    suspend fun requestFollowOrUnFollow(
        myEmail    : String,
        targetEmail: String
    ): Flow<ApiResult<User>>

    suspend fun deleteUser(email: String)

    suspend fun insertOrUpdateMyProfile(user: User)
}