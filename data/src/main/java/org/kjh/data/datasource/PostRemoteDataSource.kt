package org.kjh.data.datasource

import org.kjh.data.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.kjh.data.model.base.BaseApiModel
import org.kjh.data.model.PostModel
import org.kjh.data.model.UserModel
import java.io.File
import javax.inject.Inject

/**
 * MyTravel
 * Class: PostRemoteDataSource
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PostRemoteDataSource {

    suspend fun uploadPost(
        file        : List<String>,
        email       : String,
        content     : String? = null,
        placeName   : String,
        placeAddress: String,
        placeRoadAddress: String,
        x : String,
        y : String
    ): BaseApiModel<UserModel>

    suspend fun fetchLatestPosts(
        page: Int,
        size: Int
    ): BaseApiModel<List<PostModel>>
}

class PostRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): PostRemoteDataSource {

    override suspend fun uploadPost(
        file: List<String>,
        email: String,
        content: String?,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ): BaseApiModel<UserModel> {

        val fileBody = file.map {
            val tempFile = File(it)
            val requestFile = tempFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        }

        return apiService.uploadPost(
            file = fileBody,
            email = email,
            content = content,
            placeName = placeName,
            placeAddress = placeAddress,
            placeRoadAddress = placeRoadAddress,
            x = x,
            y = y
        )
    }

    override suspend fun fetchLatestPosts(
        page: Int,
        size: Int
    ) = apiService.fetchLatestPosts(page, size)
}