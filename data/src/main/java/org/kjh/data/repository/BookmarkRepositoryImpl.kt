package org.kjh.data.repository

import org.kjh.data.datasource.BookmarkRemoteDataSource
import org.kjh.data.mapper.ResponseMapper
import org.kjh.domain.entity.ApiResult
import org.kjh.domain.entity.BookmarkEntity
import org.kjh.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * MyTravel
 * Class: BookmarkRepositoryImpl
 * Created by jonghyukkang on 2022/04/05.
 *
 * Description:
 */
class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkRemoteDataSource: BookmarkRemoteDataSource
): BookmarkRepository {

    override suspend fun fetchMyBookmarks(
        myEmail: String
    ): Flow<ApiResult<List<BookmarkEntity>>> = flow {
        emit(ApiResult.Loading)

        val response = bookmarkRemoteDataSource.fetchMyBookmarks(myEmail)
        emit(ResponseMapper.responseToBookMark(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToBookMark(ApiResult.Error(it)))
    }

    override suspend fun updateMyBookmarks(
        myEmail  : String,
        postId   : Int,
        placeName: String
    ): Flow<ApiResult<List<BookmarkEntity>>> = flow{
        emit(ApiResult.Loading)

        val response = bookmarkRemoteDataSource.updateMyBookmarks(myEmail, postId, placeName)
        emit(ResponseMapper.responseToBookMark(ApiResult.Success(response.data)))
    }.catch {
        emit(ResponseMapper.responseToBookMark(ApiResult.Error(it)))
    }
}