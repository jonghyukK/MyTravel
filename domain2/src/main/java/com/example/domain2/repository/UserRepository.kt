package com.example.domain2.repository

import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.BookmarkEntity
import com.example.domain2.entity.FollowEntity
import com.example.domain2.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

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
    ): Flow<ApiResult<UserEntity>>

    suspend fun getUser(
        myEmail    : String,
        targetEmail: String? = null
    ): Flow<ApiResult<UserEntity>>

    suspend fun updateUserProfile(
        profileUrl  : String?,
        email       : String,
        nickName    : String,
        introduce   : String?
    ): Flow<ApiResult<UserEntity>>

    suspend fun requestFollowOrUnFollow(
        myEmail    : String,
        targetEmail: String
    ): Flow<ApiResult<FollowEntity>>
}