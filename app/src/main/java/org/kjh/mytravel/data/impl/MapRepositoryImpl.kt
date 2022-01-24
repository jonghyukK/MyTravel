package org.kjh.mytravel.data.impl

import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.KakaoApiService
import org.kjh.mytravel.data.model.KakaoSearchResponse
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.MapRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: MapRepositoryImpl
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */

private const val KAKAO_API_KEY = "KakaoAK d31f3f8af4fd669cc13affa47d78a29e"

@Singleton
class MapRepositoryImpl @Inject constructor(
    val kakaoApiService: KakaoApiService,
    val responseToSearchResult : (Result<KakaoSearchResponse>) -> Result<KakaoSearchResponse>
): MapRepository {

    override fun searchPlace(
        query: String
    ): Flow<Result<KakaoSearchResponse>> = flow {
        emit(Result.Loading())

        try {
            val result = kakaoApiService.getPlaceInfo(
                key   = KAKAO_API_KEY,
                query = query)

            Logger.d("kakao Result :${result}")

            emit(responseToSearchResult(Result.Success(result)))
        } catch (e: Exception) {
            emit(responseToSearchResult(Result.Error(e)))
        }
    }
}