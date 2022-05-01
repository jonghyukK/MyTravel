package org.kjh.domain.repository

import androidx.paging.PagingData
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.PostEntity
import org.kjh.domain.entity.UserEntity
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