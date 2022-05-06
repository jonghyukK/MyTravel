package org.kjh.data.datasource

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kjh.data.api.ApiService
import org.kjh.data.model.base.BaseApiModel
import org.kjh.data.model.FollowModel
import org.kjh.data.model.UserModel
import java.io.File
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
    private val apiService: ApiService
): UserRemoteDataSource {
    override suspend fun fetchMyProfile(myEmail: String)
    = apiService.fetchUser(myEmail)

    override suspend fun fetchUser(myEmail: String, targetEmail: String?)
    = apiService.fetchUser(myEmail, targetEmail)

    override suspend fun updateMyProfile(
        profileUrl: String?,
        email: String,
        nickName: String,
        introduce: String?
    ): BaseApiModel<UserModel> {
        var filePath = profileUrl

        if (profileUrl != null && profileUrl.startsWith("/data")) {
            filePath = profileUrl.replace("${email}_", "")
        }

        val file = filePath?.let {
            val tempFile = File(filePath)
            val requestFile = tempFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        } ?: MultipartBody.Part.createFormData("empty", "empty")

        return apiService.updateMyProfile(
            file = file,
            email = email,
            nickName = nickName,
            introduce = introduce)
    }

    override suspend fun updateFollowState(
        myEmail    : String,
        targetEmail: String
    ) = apiService.updateFollowState(myEmail, targetEmail)
}