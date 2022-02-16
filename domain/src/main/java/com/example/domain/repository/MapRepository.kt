package com.example.domain.repository

import com.example.domain.entity.ApiResult
import com.example.domain.entity.MapSearch
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: MapRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface MapRepository {
    suspend fun searchPlace(query: String): Flow<ApiResult<List<MapSearch>>>
}