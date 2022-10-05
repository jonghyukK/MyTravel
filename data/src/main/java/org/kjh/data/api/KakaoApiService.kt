package org.kjh.data.api

import org.kjh.data.model.api.MapQueryApiModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * MyTravel
 * Class: KakaoApiService
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface KakaoApiService {

    @GET("v2/local/search/keyword.json")
    suspend fun getPlaceInfo(
        @Header("Authorization") key  : String,
        @Query("query"         ) query: String
    ): MapQueryApiModel
}