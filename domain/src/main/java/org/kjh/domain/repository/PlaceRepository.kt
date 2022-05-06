package org.kjh.domain.repository

import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.BannerEntity
import org.kjh.domain.entity.PlaceEntity
import org.kjh.domain.entity.PlaceWithRankEntity
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: PlaceRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PlaceRepository {
    suspend fun fetchPlaceDetailByPlaceName(
        placeName: String
    ): Flow<ApiResult<PlaceEntity>>

    suspend fun fetchPlaceRankings()
    : Flow<ApiResult<List<PlaceWithRankEntity>>>

    suspend fun fetchPlaceBanners()
    : Flow<ApiResult<List<BannerEntity>>>

    suspend fun fetchPlacesBySubCityName(
        subCityName: String
    ): Flow<ApiResult<List<PlaceEntity>>>
}