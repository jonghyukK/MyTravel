package org.kjh.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.kjh.data.datasource.UserRemoteDataSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.FollowEntity
import org.kjh.domain.entity.UserEntity
import org.kjh.domain.repository.UserRepository
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

    override suspend fun fetchMyProfile(
        myEmail: String
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val response = userRemoteDataSource.fetchMyProfile(myEmail)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun fetchUser(
        myEmail     : String,
        targetEmail : String?
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val response = userRemoteDataSource.fetchUser(myEmail, targetEmail)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun updateMyProfile(
        profileUrl  : String?,
        email       : String,
        nickName    : String,
        introduce   : String?
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val response = userRemoteDataSource.updateMyProfile(profileUrl, email, nickName, introduce)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun updateFollowState(
        myEmail    : String,
        targetEmail: String
    ): Flow<ApiResult<FollowEntity>> = flow {
        emit(ApiResult.Loading)

        val response = userRemoteDataSource.updateFollowState(myEmail, targetEmail)
        emit(ResponseMapper.responseToFollowEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToFollowEntity(ApiResult.Error(it)))
    }
}