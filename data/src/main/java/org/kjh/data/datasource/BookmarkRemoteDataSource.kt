package org.kjh.data.datasource

import org.kjh.data.api.ApiService
import org.kjh.data.model.api.BookmarksApiModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: BookmarkRemoteDataSource
 * Created by jonghyukkang on 2022/04/05.
 *
 * Description:
 */
interface BookmarkRemoteDataSource {
    suspend fun getMyBookmarks(myEmail: String): BookmarksApiModel

    suspend fun updateBookmark(
        myEmail: String,
        postId: Int,
        placeName: String
    ): BookmarksApiModel
}

class BookmarkRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): BookmarkRemoteDataSource {

    override suspend fun getMyBookmarks(myEmail: String) =
        apiService.getBookmarks(myEmail)

    override suspend fun updateBookmark(
        myEmail: String,
        postId: Int,
        placeName: String
    ) = apiService.updateBookmark(myEmail, postId, placeName)

}