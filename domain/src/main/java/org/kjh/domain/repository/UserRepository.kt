package org.kjh.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.domain.entity.*

/**
 * MyTravel
 * Class: UserRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface UserRepository {

    val myProfileFlow: Flow<UserEntity?>

    suspend fun fetchUser(targetEmail: String): Flow<ApiResult<UserEntity>>

    suspend fun requestLogin(
        email: String,
        pw   : String
    ): Flow<ApiResult<UserEntity>>

    suspend fun requestSignUp(
        email: String,
        pw   : String,
        nick : String
    ): Flow<ApiResult<UserEntity>>

    suspend fun updateMyProfile(
        profileUrl  : String?,
        nickName    : String,
        introduce   : String?
    ): Flow<ApiResult<UserEntity>>

    suspend fun updateFollowState(
        myEmail    : String,
        targetEmail: String
    ): Flow<ApiResult<FollowEntity>>

    suspend fun updateMyBookmarks(placeName: String): Flow<ApiResult<List<BookmarkEntity>>>

    suspend fun deleteMyProfile()

    suspend fun deleteMyDayLog(dayLogId: Int): Flow<ApiResult<DeleteDayLogEntity>>

    suspend fun uploadDayLog(
        file        : List<String>,
        content     : String? = null,
        placeName   : String,
        placeAddress: String,
        placeRoadAddress: String,
        x : String,
        y : String
    ): Flow<ApiResult<UserEntity>>
}