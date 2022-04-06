package com.example.data.repository

import androidx.lifecycle.liveData
import com.example.data.datasource.BookmarkRemoteDataSource
import com.example.data.mapper.ResponseMapper
import com.example.domain2.entity.ApiResult
import com.example.domain2.entity.BookmarkEntity
import com.example.domain2.repository.BookmarkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
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

    override suspend fun getMyBookmarkList(
        myEmail: String
    ): Flow<ApiResult<List<BookmarkEntity>>> = flow {
        emit(ApiResult.Loading())

        val response = bookmarkRemoteDataSource.getMyBookmarks(myEmail)
        emit(ResponseMapper.responseToBookMark(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToBookMark(ApiResult.Error(it)))
    }

    override suspend fun updateBookmark(
        myEmail: String,
        postId: Int,
        placeName: String
    ): Flow<ApiResult<List<BookmarkEntity>>> = flow{
        emit(ApiResult.Loading())

        val response = bookmarkRemoteDataSource.updateBookmark(myEmail, postId, placeName)
        emit(ResponseMapper.responseToBookMark(ApiResult.Success(response)))
    }.catch {
        emit(ResponseMapper.responseToBookMark(ApiResult.Error(it)))
    }
}