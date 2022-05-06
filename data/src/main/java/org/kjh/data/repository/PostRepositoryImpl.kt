package org.kjh.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import org.kjh.data.datasource.PostRemoteDataSource
import org.kjh.data.datasource.RecentPostsPagingSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.PostEntity
import org.kjh.domain.entity.UserEntity
import org.kjh.domain.repository.PostRepository
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

    override suspend fun uploadPost(
        file: List<String>,
        email: String,
        content: String?,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ): Flow<ApiResult<UserEntity>> = flow {
        emit(ApiResult.Loading)

        val response = postRemoteDataSource.uploadPost(file, email, content, placeName, placeAddress, placeRoadAddress, x, y)
        emit(ResponseMapper.responseToUserEntity(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToUserEntity(ApiResult.Error(it)))
    }

    override fun fetchLatestPosts()
    : Flow<PagingData<PostEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RecentPostsPagingSource(postRemoteDataSource)}
        ).flow
    }
}