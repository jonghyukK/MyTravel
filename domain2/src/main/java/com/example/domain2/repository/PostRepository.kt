package com.example.domain2.repository

import androidx.paging.PagingData
import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.PostEntity
import com.example.domain2.entity.UserEntity
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
    ): Flow<ApiResult<UserEntity>>

    fun getRecentPostsPagingData(): Flow<PagingData<PostEntity>>
}