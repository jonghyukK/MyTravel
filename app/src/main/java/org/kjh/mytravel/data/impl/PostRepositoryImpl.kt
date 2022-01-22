package org.kjh.mytravel.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import org.kjh.mytravel.ApiService
import org.kjh.mytravel.data.mapper.ResponseMapper
import org.kjh.mytravel.data.mapper.ResponseMapper.responseToUploadResult
import org.kjh.mytravel.data.model.PlaceModel
import org.kjh.mytravel.data.model.PostUploadModel
import org.kjh.mytravel.data.model.PostUploadResponse
import org.kjh.mytravel.domain.Result
import org.kjh.mytravel.domain.repository.PostRepository
import java.lang.Exception
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

    override fun makeRequestPostUpload(
        file: List<MultipartBody.Part>,
        email: String,
        content: String?,
        cityName: String,
        placeName: String,
        placeAddress: String
    ): Flow<Result<PostUploadResponse>> = flow {
        emit(Result.Loading())

        try {
            val result = apiService.makeRequestPostUpload(
                file         = file,
                email        = email,
                content      = content,
                cityName     = cityName,
                placeName    = placeName,
                placeAddress = placeAddress
            )

            emit(responseToUploadResult(Result.Success(result)))
        } catch(e: Exception) {
            emit(responseToUploadResult(Result.Error(e)))
        }
    }
}