package org.kjh.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.kjh.data.datasource.PlaceRemoteDataSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.BannerEntity
import org.kjh.domain.entity.PlaceEntity
import org.kjh.domain.entity.PlaceWithRankEntity
import org.kjh.domain.repository.PlaceRepository
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

    override suspend fun fetchPlaceDetailByPlaceName(
        placeName: String
    ): Flow<ApiResult<PlaceEntity>> = flow {
        emit(ApiResult.Loading)

        val response = placeRemoteDataSource.fetchPlaceDetailByPlaceName(placeName)
        emit(ResponseMapper.responseToPlace(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToPlace(ApiResult.Error(it)))
    }

    override suspend fun fetchPlaceRankings()
    : Flow<ApiResult<List<PlaceWithRankEntity>>> = flow {
        emit(ApiResult.Loading)

        val response = placeRemoteDataSource.fetchPlaceRankings()
        emit(ResponseMapper.responseToPlaceRankingList(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToPlaceRankingList(ApiResult.Error(it)))
    }

    override suspend fun fetchPlaceBanners()
    : Flow<ApiResult<List<BannerEntity>>> = flow {
        emit(ApiResult.Loading)

        val response = placeRemoteDataSource.fetchPlaceBanners()
        emit(ResponseMapper.responseToBannerEntityList(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToBannerEntityList(ApiResult.Error(it)))
    }

    override suspend fun fetchPlacesBySubCityName(subCityName: String)
    : Flow<ApiResult<List<PlaceEntity>>> = flow {
        emit(ApiResult.Loading)

        val response = placeRemoteDataSource.fetchPlacesBySubCityName(subCityName)
        emit(ResponseMapper.responseToPlaceList(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToPlaceList(ApiResult.Error(it)))
    }
}