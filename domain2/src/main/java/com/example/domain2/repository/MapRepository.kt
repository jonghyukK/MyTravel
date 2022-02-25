package com.example.domain2.repository

import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.MapQueryEntity
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