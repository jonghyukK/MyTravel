package com.example.data.repository

import com.example.data.datasource.MapRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.MapQueryEntity
import com.example.domain2.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: MapRepositoryImpl
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class MapRepositoryImpl @Inject constructor(
    private val mapRemoteDataSource: MapRemoteDataSource
): MapRepository {
    override suspend fun searchPlace(query: String): Flow<ApiResult<List<MapQueryEntity>>> = flow {
        emit(ApiResult.Loading())

        val response = mapRemoteDataSource.searchPlace(query)
        emit(ResponseMapper.responseToMapQuery(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToMapQuery(ApiResult.Error(it)))
    }
}