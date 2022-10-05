package org.kjh.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.data.datasource.UserLocalDataSource
import org.kjh.data.datasource.UserRemoteDataSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.data.model.mapToDomain
import org.kjh.domain.entity.*
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
    private val eventHandler        : EventHandler,
    private val userLocalDataSource : UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override val myProfileFlow: Flow<UserEntity?> = flow {
        userLocalDataSource.getUserOrNull().collect { profileFromDB ->
            if (profileFromDB == null) {
                emit(null)
            } else {
                emit(profileFromDB.mapToDomain())
            }
        }
    }

    override suspend fun fetchUser(
        targetEmail: String
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val myProfileFromDB = userLocalDataSource.getUserOrNull()

        val response = userRemoteDataSource.fetchUser(
            myEmail     = myProfileFromDB.first()?.email,
            targetEmail = targetEmail
        )

        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun updateMyProfile(
        profileUrl  : String?,
        nickName    : String,
        introduce   : String?
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val response = userRemoteDataSource.updateMyProfile(
            profileUrl  = profileUrl,
            email       = getMyProfileFromLocal().email,
            nickName    = nickName,
            introduce   = introduce
        )

        userLocalDataSource.updateUser(response.data)
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

    override suspend fun deleteMyProfile() {
        userLocalDataSource.deleteAll()
        eventHandler.emitEvent(Event.LogoutEvent)
    }

    override suspend fun requestLogin(
        email: String,
        pw   : String
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val response = userRemoteDataSource.requestLogin(email, pw)

        if (!response.result) {
            throw Exception(response.errorMsg)
        }

        userLocalDataSource.saveUser(response.data)
        eventHandler.emitEvent(Event.LoginEvent)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun requestSignUp(
        email: String,
        pw   : String,
        nick : String
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val response = userRemoteDataSource.requestSignUp(email, pw, nick)

        if (!response.result) {
            throw Exception(response.errorMsg)
        }

        userLocalDataSource.saveUser(response.data)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun deleteMyDayLog(dayLogId: Int): Flow<ApiResult<DeleteDayLogEntity>> =
        flow {
            emit(ApiResult.Loading)

            val response = userRemoteDataSource.deleteMyDayLog(
                dayLogId = dayLogId,
                email = getMyProfileFromLocal().email
            )

            if (!response.result) {
                throw Exception(response.errorMsg)
            }

            userLocalDataSource.saveUser(response.data)
            eventHandler.emitEvent(Event.DeleteDayLog(dayLogId))
            emit(ApiResult.Success(DeleteDayLogEntity(deletedDayLogId = dayLogId)))
        }.catch {
            emit(ApiResult.Error(it))
        }

    override suspend fun uploadDayLog(
        file: List<String>,
        content: String?,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val response = userRemoteDataSource.uploadDayLog(
            file, getMyProfileFromLocal().email, content, placeName, placeAddress, placeRoadAddress, x, y)

        userLocalDataSource.updateUser(response.data)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override suspend fun updateMyBookmarks(placeName: String): Flow<ApiResult<List<BookmarkEntity>>> =
        flow {
            emit(ApiResult.Loading)

            val myProfileFromDb = getMyProfileFromLocal()
            val response = userRemoteDataSource.updateMyBookmarks(myProfileFromDb.email, placeName)

            if (!response.result) {
                throw Exception(response.errorMsg)
            }

            val newBookmarks = response.data
            val newUser = myProfileFromDb.copy(
                bookMarks = newBookmarks,
                dayLogs   = myProfileFromDb.dayLogs.map { dayLog ->
                    dayLog.copy(
                        isBookmarked = newBookmarks.any { dayLog.placeName == it.placeName }
                    )
                }
            )

            userLocalDataSource.updateUser(newUser)
            eventHandler.emitEvent(Event.UpdateBookmark(response.data, placeName))
            emit(ResponseMapper.responseToBookMark(ApiResult.Success(response.data)))
        }.catch {
            emit(ResponseMapper.responseToBookMark(ApiResult.Error(it)))
        }

    private suspend fun getMyProfileFromLocal() =
        userLocalDataSource.getUser().first()
}