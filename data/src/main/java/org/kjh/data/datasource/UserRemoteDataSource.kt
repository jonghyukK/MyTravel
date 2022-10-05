package org.kjh.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.kjh.data.api.ApiService
import org.kjh.data.model.BookmarkModel
import org.kjh.data.model.FollowModel
import org.kjh.data.model.UserModel
import org.kjh.data.model.base.BaseApiModel
import org.kjh.data.utils.FileUtils
import javax.inject.Inject

/**
 * MyTravel
 * Class: UserDataSource
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface UserRemoteDataSource {

    suspend fun fetchUser(
        myEmail    : String?,
        targetEmail: String
    ): BaseApiModel<UserModel>

    suspend fun updateMyProfile(
        profileUrl: String?,
        email     : String,
        nickName  : String,
        introduce : String?
    ): BaseApiModel<UserModel>

    suspend fun updateFollowState(
        myEmail    : String,
        targetEmail: String
    ): BaseApiModel<FollowModel>

    suspend fun requestLogin(
        email: String,
        pw   : String
    ): BaseApiModel<UserModel>

    suspend fun requestSignUp(
        email   : String,
        pw      : String,
        nickName: String
    ): BaseApiModel<UserModel>

    suspend fun deleteMyDayLog(
        dayLogId: Int,
        email : String
    ): BaseApiModel<UserModel>

    suspend fun updateMyBookmarks(
        myEmail  : String,
        placeName: String
    ): BaseApiModel<List<BookmarkModel>>

    suspend fun uploadDayLog(
        file        : List<String>,
        email       : String,
        content     : String? = null,
        placeName   : String,
        placeAddress: String,
        placeRoadAddress: String,
        x : String,
        y : String
    ): BaseApiModel<UserModel>
}

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): UserRemoteDataSource {

    override suspend fun fetchUser(myEmail: String?, targetEmail: String)
    = withContext(ioDispatcher) {
        apiService.fetchUser(myEmail, targetEmail)
    }

    override suspend fun updateMyProfile(
        profileUrl: String?,
        email     : String,
        nickName  : String,
        introduce : String?
    ): BaseApiModel<UserModel>
    = withContext(ioDispatcher) {
        apiService.updateMyProfile(
            file      = FileUtils.makeFormDataForUpload(profileUrl),
            email     = email,
            nickName  = nickName,
            introduce = introduce)
    }

    override suspend fun updateFollowState(
        myEmail    : String,
        targetEmail: String
    ) = withContext(ioDispatcher) {
        apiService.updateFollowState(myEmail, targetEmail)
    }

    override suspend fun requestLogin(
        email: String,
        pw   : String
    ) = withContext(ioDispatcher) {
        apiService.requestLogin(email, pw)
    }

    override suspend fun requestSignUp(
        email   : String,
        pw      : String,
        nickName: String
    ) = withContext(ioDispatcher) {
        apiService.createUser(email, pw, nickName)
    }

    override suspend fun deleteMyDayLog(
        dayLogId: Int,
        email: String
    ) = withContext(ioDispatcher) {
        apiService.deleteDayLog(dayLogId, email)
    }

    override suspend fun updateMyBookmarks(
        myEmail  : String,
        placeName: String
    ) = withContext(ioDispatcher) {
        apiService.updateMyBookmarks(myEmail, placeName)
    }

    override suspend fun uploadDayLog(
        file        : List<String>,
        email       : String,
        content     : String?,
        placeName   : String,
        placeAddress: String,
        placeRoadAddress: String,
        x           : String,
        y           : String
    ) = withContext(ioDispatcher) {
        apiService.uploadDayLog(
            x            = x,
            y            = y,
            file         = FileUtils.makeFormDataListForUpload(file),
            email        = email,
            content      = content,
            placeName    = placeName,
            placeAddress = placeAddress,
            placeRoadAddress = placeRoadAddress
        )
    }
}