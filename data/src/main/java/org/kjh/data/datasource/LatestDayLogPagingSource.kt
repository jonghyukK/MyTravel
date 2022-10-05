package org.kjh.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.kjh.data.model.mapToDomain
import org.kjh.data.repository.DayLogRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import org.kjh.domain.entity.DayLogEntity
import retrofit2.HttpException
import java.io.IOException

/**
 * MyTravel
 * Class: LatestDayLogPagingSource
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */



private const val STARTING_PAGE_INDEX = 0

class LatestDayLogPagingSource(
    private val dayLogRemoteDataSource: DayLogRemoteDataSource,
): PagingSource<Int, DayLogEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DayLogEntity> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = dayLogRemoteDataSource.fetchLatestDayLogs(position, params.loadSize)
            val dayLogItems = response.data
            val nextKey = if (dayLogItems.isEmpty() || dayLogItems.size < NETWORK_PAGE_SIZE) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            Log.e("LatestDayLogPagingSource", """
                ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
                startPos        : $position
                loadSize        : ${params.loadSize}
                itemCount       : ${dayLogItems.size}
                nextKey         : $nextKey
                prevKey         : ${if (position == STARTING_PAGE_INDEX) null else position - 1}
                ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
            """.trimIndent())

           LoadResult.Page(
                data = dayLogItems.map { it.mapToDomain() },
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DayLogEntity>): Int? {
        Log.e("LatestDayLogPagingSource", " getRefreshKey")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}