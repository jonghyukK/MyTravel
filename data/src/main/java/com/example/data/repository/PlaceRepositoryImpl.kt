package com.example.data.repository

import com.example.data.datasource.PlaceRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.PlaceEntity
import com.example.domain2.entity.PlaceWithRankEntity
import com.example.domain2.repository.PlaceRepository
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
        placeName: String
    ): Flow<ApiResult<PlaceEntity>> = flow {
        emit(ApiResult.Loading())

        val response = placeRemoteDataSource.getPlace(placeName)
        emit(ResponseMapper.responseToPlace(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToPlace(ApiResult.Error(it)))
    }

    override suspend fun getPlaceRanking()
    : Flow<ApiResult<List<PlaceWithRankEntity>>> = flow {
        emit(ApiResult.Loading())

        val response = placeRemoteDataSource.getPlaceRanking()
        emit(ResponseMapper.responseToPlaceRankingList(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToPlaceRankingList(ApiResult.Error(it)))
    }
}