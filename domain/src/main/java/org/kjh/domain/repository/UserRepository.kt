package org.kjh.domain.repository

import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.FollowEntity
import org.kjh.domain.entity.UserEntity
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