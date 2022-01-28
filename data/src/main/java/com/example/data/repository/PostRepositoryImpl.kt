package com.example.data.repository

import com.example.data.datasource.PostRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain.entity.ApiResult
import com.example.domain.entity.UploadPostResponse
import com.example.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * MyTravel
 * Class: PostRepositoryImpl
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
): PostRepository {

    override suspend fun makeRequestUploadPost(
        file: List<String>,
        email: String,
        content: String?,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ): Flow<ApiResult<UploadPostResponse>> = flow {
        emit(ApiResult.Loading())

        val response = postRemoteDataSource.uploadPost(file, email, content, placeName, placeAddress, placeRoadAddress, x, y)

        emit(ResponseMapper.responseToUploadPost(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUploadPost(ApiResult.Error(it)))
    }
}