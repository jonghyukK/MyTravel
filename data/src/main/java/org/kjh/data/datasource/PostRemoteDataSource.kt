package org.kjh.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kjh.data.api.ApiService
import org.kjh.data.model.PostModel
import org.kjh.data.model.UserModel
import org.kjh.data.model.base.BaseApiModel
import org.kjh.data.utils.FileUtils
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
        file        : List<String>,
        email       : String,
        content     : String?,
        placeName   : String,
        placeAddress: String,
        placeRoadAddress: String,
        x           : String,
        y           : String
    ) = withContext(Dispatchers.IO) {
        apiService.uploadPost(
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


    override suspend fun fetchLatestPosts(
        page: Int,
        size: Int
    ) = withContext(Dispatchers.IO) {
        apiService.fetchLatestPosts(page, size)
    }
}