package com.example.data.datasource

import com.example.data.api.ApiService
import com.example.domain.entity.BookmarkResponse
import com.example.domain.entity.UpdateProfile
import com.example.domain.entity.UserResponse
import kotlinx.coroutines.flow.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
    suspend fun getMyProfile(myEmail: String): UserResponse

    suspend fun getUser(myEmail: String, targetEmail: String? = null): UserResponse

    suspend fun makeRequestProfileUpdate(
        profileUrl: String?,
        email: String,
        nickName: String,
        introduce: String?
    ): UpdateProfile

    suspend fun requestFollowOrUnFollow(
        myEmail: String,
        targetEmail: String
    ): UserResponse

    suspend fun updateBookmark(
        email: String,
        postId: Int,
        placeName: String
    ): BookmarkResponse
}

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): UserRemoteDataSource {
    override suspend fun getMyProfile(myEmail: String) =
        apiService.getUser(myEmail)

    override suspend fun getUser(myEmail: String, targetEmail: String?) =
        apiService.getUser(myEmail, targetEmail)

    override suspend fun makeRequestProfileUpdate(
        profileUrl: String?,
        email: String,
        nickName: String,
        introduce: String?
    ): UpdateProfile {
        var filePath = profileUrl

        if (profileUrl != null && profileUrl.startsWith("/data")) {
            filePath = profileUrl.replace("${email}_", "")
        }

        val file = filePath?.let {
            val tempFile = File(filePath)
            val requestFile = tempFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        } ?: MultipartBody.Part.createFormData("empty", "empty")

        return apiService.updateUser(
            file = file,
            email = email,
            nickName = nickName,
            introduce = introduce)
    }

    override suspend fun requestFollowOrUnFollow(
        myEmail    : String,
        targetEmail: String
    ) = apiService.requestFollowOrUnFollow(myEmail, targetEmail)

    override suspend fun updateBookmark(
        email: String,
        postId: Int,
        placeName: String
    ) = apiService.updateBookmark(email, postId, placeName)

}