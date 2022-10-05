package org.kjh.domain.repository

import androidx.paging.PagingData
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.DayLogEntity
import org.kjh.domain.entity.UserEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: DayLogRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

interface DayLogRepository {
    fun fetchLatestDayLogs(): Flow<PagingData<DayLogEntity>>
}