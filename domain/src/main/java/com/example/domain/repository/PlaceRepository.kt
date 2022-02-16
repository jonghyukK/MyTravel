package com.example.domain.repository

import com.example.domain.entity.ApiResult
import com.example.domain.entity.Place
import kotlinx.coroutines.flow.Flow

/**
 * MyTravel
 * Class: PlaceRepository
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
interface PlaceRepository {
    suspend fun getPlace(myEmail: String, placeName: String): Flow<ApiResult<Place>>
}