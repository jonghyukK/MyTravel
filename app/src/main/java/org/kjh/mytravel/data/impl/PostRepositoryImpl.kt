package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.mapper.ResponseMapper.responseToUploadResult
import org.kjh.mytravel.data.model.PlaceModel
import org.kjh.mytravel.data.model.PostUploadModel
import org.kjh.mytravel.data.model.PostUploadResponse
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.PostRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MyTravel
 * Class: PostRepositoryImpl
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */

@Singleton
class PostRepositoryImpl @Inject constructor(
    val apiService: ApiService,
    val responseToUploadResult: (Result<PostUploadResponse>) -> Result<PostUploadResponse>
): PostRepository {

    override fun upload(postUploadModel: PostUploadModel)
    : Flow<Result<PostUploadResponse>> = flow {
        emit(Result.Loading())

        val response = PostUploadResponse(
            result = "success"
        )

        emit(responseToUploadResult(Result.Success(response)))
    }
}