package org.kjh.domain.repository

import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: BookmarkRepository
 * Created by jonghyukkang on 2022/04/05.
 *
 * Description:
 */
interface BookmarkRepository {

    suspend fun fetchMyBookmarks(
        myEmail: String
    ): Flow<ApiResult<List<BookmarkEntity>>>

    suspend fun updateMyBookmarks(
        myEmail  : String,
        postId   : Int,
        placeName: String
    ): Flow<ApiResult<List<BookmarkEntity>>>
}