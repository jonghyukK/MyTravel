package com.example.data.repository

import com.example.data.datasource.PlaceRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain.entity.ApiResult
import com.example.domain.entity.Place
import com.example.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: PlaceRepositoryImpl
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class PlaceRepositoryImpl @Inject constructor(
    private val placeRemoteDataSource: PlaceRemoteDataSource
): PlaceRepository {
    override suspend fun getPlace(
        myEmail: String,
        placeName: String
    ): Flow<ApiResult<Place>> = flow {
        emit(ApiResult.Loading())

        val response = placeRemoteDataSource.getPlace(myEmail, placeName)
        emit(ResponseMapper.responseToPlace(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToPlace(ApiResult.Error(it)))
    }
}