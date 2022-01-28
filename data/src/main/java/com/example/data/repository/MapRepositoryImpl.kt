package com.example.data.repository

import com.example.data.datasource.MapRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain.entity.ApiResult
import com.example.domain.entity.MapSearch
import com.example.domain.repository.MapRepository
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
    override suspend fun searchPlace(query: String): Flow<ApiResult<List<MapSearch>>> = flow {
        emit(ApiResult.Loading())

        val response = mapRemoteDataSource.searchPlace(query)
        emit(ResponseMapper.responseToMapSearch(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToMapSearch(ApiResult.Error(it)))
    }
}