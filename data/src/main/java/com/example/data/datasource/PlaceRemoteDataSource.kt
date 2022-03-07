package com.example.data.datasource

import com.example.data.api.ApiService
import com.example.data.model.api.BannersApiModel
import com.example.data.model.api.PlaceApiModel
import com.example.data.model.api.PlaceRankingApiModel
import com.example.data.model.api.PlacesApiModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: PlaceRemoteDataSource
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PlaceRemoteDataSource {
    suspend fun getPlace(placeName: String): PlaceApiModel

    suspend fun getPlaceRanking(): PlaceRankingApiModel

    suspend fun getPlaceBanners(): BannersApiModel
}

class PlaceRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): PlaceRemoteDataSource {

    override suspend fun getPlace(placeName: String) =
        apiService.getPlaceByPlaceName(placeName)

    override suspend fun getPlaceRanking() =
        apiService.getPlaceRanking()

    override suspend fun getPlaceBanners() =
        apiService.getHomeBanners()
}