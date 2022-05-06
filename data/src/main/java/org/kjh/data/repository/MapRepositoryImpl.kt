package org.kjh.data.repository

import org.kjh.data.datasource.MapRemoteDataSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.MapQueryEntity
import org.kjh.domain.repository.MapRepository
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
        emit(ApiResult.Loading)

        val response = mapRemoteDataSource.searchPlace(query)
        emit(ResponseMapper.responseToMapQuery(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToMapQuery(ApiResult.Error(it)))
    }
}