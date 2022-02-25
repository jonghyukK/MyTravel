package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.PostRemoteDataSource
import com.example.data.datasource.RecentPostsPagingSource
import com.example.data.mapper.ResponseMapper
import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.PostEntity
import com.example.domain2.entity.UserEntity
import com.example.domain2.repository.PostRepository
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
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading())

        val response = postRemoteDataSource.uploadPost(file, email, content, placeName, placeAddress, placeRoadAddress, x, y)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override fun getRecentPostsPagingData(myEmail: String)
    : Flow<PagingData<PostEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RecentPostsPagingSource(postRemoteDataSource, myEmail)}
        ).flow
    }
}