package com.example.data.datasource

import com.example.data.api.ApiService
import com.example.data.model.PlaceResponse
import com.example.domain.entity.PlaceRankingResponse
import javax.inject.Inject

/**
 * MyTravel
 * Class: PlaceRemoteDataSource
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PlaceRemoteDataSource {
    suspend fun getPlace(placeName: String): PlaceResponse

    suspend fun getPlaceRanking(): PlaceRankingResponse
}

class PlaceRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): PlaceRemoteDataSource {

    override suspend fun getPlace(placeName: String) =
        apiService.getPlaceByPlaceName(placeName)

    override suspend fun getPlaceRanking() =
        apiService.getPlaceRanking()
}