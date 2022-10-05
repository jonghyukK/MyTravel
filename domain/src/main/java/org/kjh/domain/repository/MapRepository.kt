package org.kjh.domain.repository

import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.MapQueryEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: MapRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface MapRepository {
    suspend fun searchPlace(query: String): Flow<ApiResult<List<MapQueryEntity>>>
}