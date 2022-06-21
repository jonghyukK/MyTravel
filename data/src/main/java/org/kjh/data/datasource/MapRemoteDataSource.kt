package org.kjh.data.datasource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kjh.data.api.KakaoApiService
import org.kjh.data.model.api.MapQueryApiModel
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
    private val kakaoApiService: KakaoApiService,
    private val ioDispatcher: CoroutineDispatcher
): MapRemoteDataSource {

    override suspend fun searchPlace(query: String) =
        withContext(ioDispatcher) {
            kakaoApiService.getPlaceInfo(KAKAO_API_KEY, query)
        }
}