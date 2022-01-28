package com.example.data.repository

import com.example.data.datasource.UserRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain.entity.ApiResult
import com.example.domain.entity.UpdateProfile
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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

    override suspend fun getUser(
        myEmail     : String,
        targetEmail : String?
    ): Flow<ApiResult<User>> = flow {
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
}