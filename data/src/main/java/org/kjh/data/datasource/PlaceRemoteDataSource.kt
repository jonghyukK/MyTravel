package org.kjh.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kjh.data.api.ApiService
import org.kjh.data.model.BannerModel
import org.kjh.data.model.base.BaseApiModel
import org.kjh.data.model.PlaceModel
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

    suspend fun fetchPlaceDetailByPlaceName(placeName: String)
    : BaseApiModel<PlaceModel>

    suspend fun fetchPlaceRankings()
    : BaseApiModel<List<PlaceWithRankModel>>

    suspend fun fetchPlaceBanners()
    : BaseApiModel<List<BannerModel>>

    suspend fun fetchPlacesBySubCityName(subCityName: String)
    : BaseApiModel<List<PlaceModel>>
}

class PlaceRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): PlaceRemoteDataSource {

    override suspend fun fetchPlaceDetailByPlaceName(placeName: String)
    = withContext(Dispatchers.IO) {
        apiService.fetchPlaceDetailByPlaceName(placeName)
    }

    override suspend fun fetchPlaceRankings()
    = withContext(Dispatchers.IO) {
        apiService.fetchPlaceRankings()
    }

    override suspend fun fetchPlaceBanners()
    = withContext(Dispatchers.IO) {
        apiService.fetchPlaceBanners()
    }

    override suspend fun fetchPlacesBySubCityName(subCityName: String)
    = withContext(Dispatchers.IO) {
        apiService.fetchPlacesBySubCityName(subCityName)
    }
}