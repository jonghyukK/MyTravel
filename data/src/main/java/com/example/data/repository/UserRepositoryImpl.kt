package com.example.data.repository

import android.util.Log
import com.example.data.datasource.UserLocalDataSource
import com.example.data.datasource.UserRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain.entity.ApiResult
import com.example.domain.entity.UpdateProfile
import com.example.domain.entity.User
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * MyTravel
 * Class: UserRepositoryImpl
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */


class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
): UserRepository {

    override suspend fun getMyProfile(myEmail: String): Flow<ApiResult<User>> = flow {
        emit(ApiResult.Loading())

        val remoteData = userRemoteDataSource.getMyProfile(myEmail)

//        userLocalDataSource.insertOrUpdateMyProfile(remoteData.data)

        userLocalDataSource.getMyProfile().collect {
            it?.let {
                Log.e("ttt", "Local MyProfile : ${it}")
                emit(ResponseMapper.localResponseToUser(ApiResult.Success(it)))
            }
        }
    }.catch {
        emit(ResponseMapper.responseToUser(ApiResult.Error(it)))
    }

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

    override suspend fun requestFollowOrUnFollow(
        myEmail: String,
        targetEmail: String
    ): Flow<ApiResult<User>> = flow {
        emit(ApiResult.Loading())

        val response = userRemoteDataSource.requestFollowOrUnFollow(myEmail, targetEmail)
        val myLocalProfile = userLocalDataSource.getMyProfile().first()

        myLocalProfile?.let {
            userLocalDataSource.insertOrUpdateMyProfile(
                it.copy(
                    followingCount = if (response.data.isFollowing!!)
                        it.followingCount + 1
                    else
                        it.followingCount - 1
                )
            )
        }

        emit(ResponseMapper.responseToUser(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUser(ApiResult.Error(it)))
    }

    override suspend fun deleteUser(email: String) {
        userLocalDataSource.deleteUser(email)
    }

    override suspend fun insertOrUpdateMyProfile(user: User) {
        userLocalDataSource.insertOrUpdateMyProfile(user)
    }
}