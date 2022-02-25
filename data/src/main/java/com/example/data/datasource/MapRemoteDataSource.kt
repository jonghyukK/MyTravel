package com.example.data.datasource

import com.example.data.api.KakaoApiService
import com.example.data.model.api.MapQueryApiModel
import javax.inject.Inject

/**
 * MyTravel
 * Class: MapRemoteDataSource
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

private const val KAKAO_API_KEY = "KakaoAK d31f3f8af4fd669cc13affa47d78a29e"

interface MapRemoteDataSource {
    suspend fun searchPlace(query: String): MapQueryApiModel
}

class MapRemoteDataSourceImpl @Inject constructor(
    private val kakaoApiService: KakaoApiService
): MapRemoteDataSource {

    override suspend fun searchPlace(query: String) =
        kakaoApiService.getPlaceInfo(KAKAO_API_KEY, query)
}