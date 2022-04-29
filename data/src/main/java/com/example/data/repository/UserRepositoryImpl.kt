package com.example.data.repository

import com.example.data.datasource.UserRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.data.model.api.UserApiModel
import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.BookmarkEntity
import com.example.domain2.entity.FollowEntity
import com.example.domain2.entity.UserEntity
import com.example.domain2.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import javax.inject.Inject

/**
 * MyTravel
 * Class: UserRepositoryImpl
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun getMyProfile(
        myEmail: String
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.getMyProfile(myEmail)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun getUser(
        myEmail     : String,
        targetEmail : String?
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.getUser(myEmail, targetEmail)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun updateUserProfile(
        profileUrl  : String?,
        email       : String,
        nickName    : String,
        introduce   : String?
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.makeRequestProfileUpdate(profileUrl, email, nickName, introduce)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun requestFollowOrUnFollow(
        myEmail: String,
        targetEmail: String
    ): Flow<ApiResult<FollowEntity>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.requestFollowOrUnFollow(myEmail, targetEmail)
        emit(ResponseMapper.responseToFollowEntity(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToFollowEntity(ApiResult.Error(it)))
    }
}