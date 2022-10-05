package org.kjh.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.kjh.domain.entity.*

/**
 * MyTravel
 * Class: PlaceRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PlaceRepository {
    suspend fun fetchPlaceByPlaceName(placeName: String): Flow<ApiResult<PlaceEntity>>

    fun fetchPlaceByPlaceNameWithAround(placeName: String): Flow<PagingData<PlaceEntity>>

    suspend fun fetchPlaceRankings(): Flow<ApiResult<List<PlaceRankingEntity>>>

    suspend fun fetchPlaceBanners(): Flow<ApiResult<List<BannerEntity>>>

    suspend fun fetchPlacesBySubCityName(subCityName: String): Flow<ApiResult<List<PlaceEntity>>>
}