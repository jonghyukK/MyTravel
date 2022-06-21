package org.kjh.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): BookmarkRemoteDataSource {

    override suspend fun fetchMyBookmarks(
        myEmail: String
    ) = withContext(ioDispatcher) {
        apiService.fetchMyBookmarks(myEmail)
    }

    override suspend fun updateMyBookmarks(
        myEmail  : String,
        postId   : Int,
        placeName: String
    ) = withContext(ioDispatcher) {
        apiService.updateMyBookmarks(myEmail, postId, placeName)
    }

}