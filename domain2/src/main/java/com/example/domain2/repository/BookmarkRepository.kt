package com.example.domain2.repository

import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: BookmarkRepository
 * Created by jonghyukkang on 2022/04/05.
 *
 * Description:
 */
interface BookmarkRepository {

    suspend fun getMyBookmarkList(
        myEmail: String
    ): Flow<ApiResult<List<BookmarkEntity>>>

    suspend fun updateBookmark(
        myEmail: String,
        postId: Int,
        placeName: String
    ): Flow<ApiResult<List<BookmarkEntity>>>
}