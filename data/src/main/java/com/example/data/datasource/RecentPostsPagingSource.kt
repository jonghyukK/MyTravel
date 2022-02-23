package com.example.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.ApiService
import com.example.domain.entity.Post

/**
 * MyTravel
 * Class: RecentPostsPagingSource
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */
class RecentPostsPagingSource(
    private val postRemoteDataSource: PostRemoteDataSource,
    private val myEmail: String
): PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        try {
            val nextPageNumber = params.key ?: 0
            val response = postRemoteDataSource.getRecentPosts(myEmail, nextPageNumber, params.loadSize)

            val postItems = response.data
            val nextKey = if (postItems.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / 5)
            }

            Log.e("RecentPostsPagingSource", """
                nextPageNumber : ${nextPageNumber}
                loadSize : ${params.loadSize}
                itemCount : ${postItems.size}
                nextKey : ${nextKey}
            """.trimIndent())

            return LoadResult.Page(
                data = response.data,
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}