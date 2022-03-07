package com.example.domain2.repository

import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.BannerEntity
import com.example.domain2.entity.PlaceEntity
import com.example.domain2.entity.PlaceWithRankEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: PlaceRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PlaceRepository {
    suspend fun getPlace(placeName: String): Flow<ApiResult<PlaceEntity>>

    suspend fun getPlaceRanking(): Flow<ApiResult<List<PlaceWithRankEntity>>>

    suspend fun getPlaceBanners(): Flow<ApiResult<List<BannerEntity>>>
}