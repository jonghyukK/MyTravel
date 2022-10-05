package org.kjh.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.kjh.data.api.ApiService
import org.kjh.data.model.DayLogModel
import org.kjh.data.model.UserModel
import org.kjh.data.model.base.BaseApiModel
import org.kjh.data.utils.FileUtils
import javax.inject.Inject

/**
 * MyTravel
 * Class: DayLogRemoteDataSource
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface DayLogRemoteDataSource {
    suspend fun fetchLatestDayLogs(
        page: Int,
        size: Int
    ): BaseApiModel<List<DayLogModel>>
}

class DayLogRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): DayLogRemoteDataSource {

    override suspend fun fetchLatestDayLogs(
        page: Int,
        size: Int
    ) = withContext(ioDispatcher) {
        apiService.fetchLatestDayLogs(page, size)
    }
}