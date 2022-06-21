package org.kjh.data.datasource

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kjh.data.api.ApiService
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
    suspend fun fetchMyProfile(
        myEmail: String
    ): BaseApiModel<UserModel>

    suspend fun fetchUser(
        myEmail    : String,
        targetEmail: String? = null
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
}

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): UserRemoteDataSource {
    override suspend fun fetchMyProfile(myEmail: String)
    = withContext(ioDispatcher) {
        apiService.fetchUser(myEmail)
    }

    override suspend fun fetchUser(myEmail: String, targetEmail: String?)
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
}