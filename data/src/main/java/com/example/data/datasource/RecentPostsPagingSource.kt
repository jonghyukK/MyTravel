package com.example.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.model.mapToDomain
import com.example.domain2.entity.PostEntity

/**
 * MyTravel
 * Class: RecentPostsPagingSource
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */
class RecentPostsPagingSource(
    private val postRemoteDataSource: PostRemoteDataSource,
): PagingSource<Int, PostEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostEntity> {
        try {
            val nextPageNumber = params.key ?: 0
            val response = postRemoteDataSource.getRecentPosts(nextPageNumber, params.loadSize)

            val postItems = response.data
            val nextKey = if (postItems.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / 5)
            }

            Log.e("RecentPostsPagingSource", """
                nextPageNumber  : $nextPageNumber
                loadSize        : ${params.loadSize}
                itemCount       : ${postItems.size}
                nextKey         : $nextKey
            """.trimIndent())

            return LoadResult.Page(
                data = postItems.map { it.mapToDomain() },
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}