package com.example.domain.repository

import com.example.domain.entity.ApiResult
import com.example.domain.entity.Place
import com.example.domain.entity.PlaceRanking
import com.example.domain.entity.PlaceRankingResponse
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: PlaceRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PlaceRepository {
    suspend fun getPlace(placeName: String): Flow<ApiResult<Place>>

    suspend fun getPlaceRanking(): Flow<ApiResult<PlaceRankingResponse>>
}