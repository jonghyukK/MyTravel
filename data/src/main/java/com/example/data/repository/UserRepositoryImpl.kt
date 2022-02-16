package com.example.data.repository

import android.util.Log
import com.example.data.datasource.UserRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain.entity.*
import com.example.domain.repository.LoginPreferencesRepository
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
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
    ): Flow<ApiResult<UserResponse>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.getMyProfile(myEmail)
        emit(ResponseMapper.responseToUser(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUser(ApiResult.Error(it)))
    }

    override suspend fun getUser(
        myEmail     : String,
        targetEmail : String?
    ): Flow<ApiResult<UserResponse>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.getUser(myEmail, targetEmail)
        emit(ResponseMapper.responseToUser(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUser(ApiResult.Error(it)))
    }

    override suspend fun updateUserProfile(
        profileUrl  : String?,
        email       : String,
        nickName    : String,
        introduce   : String?
    ): Flow<ApiResult<UpdateProfile>> = flow {
        emit(ApiResult.Loading())
        
        val response = userRemoteDataSource.makeRequestProfileUpdate(profileUrl, email, nickName, introduce)
        emit(ResponseMapper.responseToUpdateProfile(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUpdateProfile(ApiResult.Error(it)))
    }

    override suspend fun requestFollowOrUnFollow(
        myEmail: String,
        targetEmail: String
    ): Flow<ApiResult<UserResponse>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.requestFollowOrUnFollow(myEmail, targetEmail)
        emit(ResponseMapper.responseToUser(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUser(ApiResult.Error(it)))
    }

    override suspend fun updateBookmark(
        email: String,
        postId: Int
    ): Flow<ApiResult<BookmarkResponse>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.updateBookmark(email, postId)
        emit(ResponseMapper.responseToBookMark(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToBookMark(ApiResult.Error(it)))
    }
}