package org.kjh.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.kjh.data.model.mapToDomain
import org.kjh.domain.entity.PlaceEntity
import org.kjh.domain.entity.PlaceWithAroundEntity
import java.lang.Exception

/**
 * MyTravel
 * Class: AroundPlacesPagingSource
 * Created by jonghyukkang on 2022/08/04.
 *
 * Description:
 */
class AroundPlacesPagingSource(
    private val placeName: String,
    private val placeRemoteDataSource: PlaceRemoteDataSource
): PagingSource<Int, PlaceEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceEntity> {
        try {
            val nextPageNumber = params.key ?: 0
            val response = placeRemoteDataSource.fetchPlaceByPlaceNameWithAround(
                placeName, nextPageNumber, params.loadSize
            )

            val placeItems = response.data
            val nextKey = if (placeItems.isEmpty()) {
                null
            } else {
                nextPageNumber + (params.loadSize / 2)
            }

            Log.e("AroundPlacesPagingSource", """
                nextPageNumber  : $nextPageNumber
                loadSize        : ${params.loadSize}
                itemCount       : ${placeItems.size}
                nextKey         : $nextKey
            """.trimIndent())

            return LoadResult.Page(
                data = placeItems.map { it.mapToDomain() },
                prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PlaceEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}