package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.entity.ApiResult
import com.example.domain.entity.Post
import com.example.domain.entity.RecentPostsResponse
import com.example.domain.entity.UploadPostResponse
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: PostRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

interface PostRepository {
    suspend fun makeRequestUploadPost(
        file        : List<String>,
        email       : String,
        content     : String? = null,
        placeName   : String,
        placeAddress: String,
        placeRoadAddress: String,
        x : String,
        y : String
    ): Flow<ApiResult<UploadPostResponse>>

    fun getRecentPostsPagingData(myEmail: String): Flow<PagingData<Post>>
}