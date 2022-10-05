package org.kjh.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.kjh.data.Event
import org.kjh.data.EventHandler
import org.kjh.data.datasource.DayLogRemoteDataSource
import org.kjh.data.datasource.LatestDayLogPagingSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.DayLogEntity
import org.kjh.domain.entity.UserEntity
import org.kjh.domain.repository.DayLogRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: DayLogRepositoryImpl
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class DayLogRepositoryImpl @Inject constructor(
    private val dayLogRemoteDataSource: DayLogRemoteDataSource
): DayLogRepository {

    override fun fetchLatestDayLogs(): Flow<PagingData<DayLogEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { LatestDayLogPagingSource(dayLogRemoteDataSource) }
        ).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 7
    }
}