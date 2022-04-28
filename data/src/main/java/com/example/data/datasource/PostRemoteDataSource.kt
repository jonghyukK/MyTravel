package com.example.data.datasource

import com.example.data.api.ApiService
import com.example.data.model.api.PostsApiModel
import com.example.data.model.api.UserApiModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
    ): UserApiModel

    suspend fun getRecentPosts(
        page: Int,
        size: Int
    ): PostsApiModel
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
    ): UserApiModel {

        val fileBody = file.map {
            val tempFile = File(it)
            val requestFile = tempFile.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        }

        return apiService.makeRequestPostUpload(
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

    override suspend fun getRecentPosts(
        page: Int,
        size: Int
    ) = apiService.getRecentPosts(page, size)
}