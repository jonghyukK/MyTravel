package org.kjh.mytravel

import org.kjh.mytravel.data.model.KakaoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * MyTravel
 * Class: KakaoApiService
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */
interface KakaoApiService {

    @GET("v2/local/search/keyword.json")
    suspend fun getPlaceInfo(
        @Header("Authorization") key  : String,
        @Query("query"         ) query: String
    ): KakaoSearchResponse
}