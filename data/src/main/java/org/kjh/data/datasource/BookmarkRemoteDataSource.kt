package org.kjh.data.datasource

import org.kjh.data.api.ApiService
import org.kjh.data.model.base.BaseApiModel
import org.kjh.data.model.BookmarkModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: BookmarkRemoteDataSource
 * Created by jonghyukkang on 2022/04/05.
 *
 * Description:
 */
interface BookmarkRemoteDataSource {
    suspend fun fetchMyBookmarks(
        myEmail: String
    ): BaseApiModel<List<BookmarkModel>>

    suspend fun updateMyBookmarks(
        myEmail  : String,
        postId   : Int,
        placeName: String
    ): BaseApiModel<List<BookmarkModel>>
}

class BookmarkRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): BookmarkRemoteDataSource {

    override suspend fun fetchMyBookmarks(
        myEmail: String
    ) = apiService.fetchMyBookmarks(myEmail)

    override suspend fun updateMyBookmarks(
        myEmail  : String,
        postId   : Int,
        placeName: String
    ) = apiService.updateMyBookmarks(myEmail, postId, placeName)

}