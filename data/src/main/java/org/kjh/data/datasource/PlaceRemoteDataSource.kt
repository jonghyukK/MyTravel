package org.kjh.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kjh.data.api.ApiService
import org.kjh.data.model.BannerModel
import org.kjh.data.model.base.BaseApiModel
import org.kjh.data.model.PlaceModel
import org.kjh.data.model.PlaceWithAroundModel
import org.kjh.data.model.PlaceWithRankModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: PlaceRemoteDataSource
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PlaceRemoteDataSource {

    suspend fun fetchPlaceByPlaceName(placeName: String)
    : BaseApiModel<PlaceModel>

    suspend fun fetchPlaceByPlaceNameWithAround(placeName: String)
    : BaseApiModel<PlaceWithAroundModel>

    suspend fun fetchPlaceRankings()
    : BaseApiModel<List<PlaceWithRankModel>>

    suspend fun fetchPlaceBanners()
    : BaseApiModel<List<BannerModel>>

    suspend fun fetchPlacesBySubCityName(subCityName: String)
    : BaseApiModel<List<PlaceModel>>
}

class PlaceRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): PlaceRemoteDataSource {

    override suspend fun fetchPlaceByPlaceName(placeName: String)
    = withContext(ioDispatcher) {
        apiService.fetchPlaceByPlaceName(placeName)
    }

    override suspend fun fetchPlaceByPlaceNameWithAround(placeName: String)
    = withContext(ioDispatcher) {
        apiService.fetchPlaceByPlaceNameWithAround(placeName)
    }

    override suspend fun fetchPlaceRankings()
    = withContext(ioDispatcher) {
        apiService.fetchPlaceRankings()
    }

    override suspend fun fetchPlaceBanners()
    = withContext(ioDispatcher) {
        apiService.fetchPlaceBanners()
    }

    override suspend fun fetchPlacesBySubCityName(subCityName: String)
    = withContext(ioDispatcher) {
        apiService.fetchPlacesBySubCityName(subCityName)
    }
}