package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import org.kjh.mytravel.data.model.PostUploadResponse
import org.kjh.mytravel.domain.Result

/**
 * MyTravel
 * Class: PostRepository
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */
interface PostRepository {

    fun makeRequestPostUpload(
        file        : List<MultipartBody.Part>,
        email       : String,
        content     : String? = null,
        placeName   : String,
        placeAddress: String,
        placeRoadAddress: String,
        x : String,
        y : String
    ): Flow<Result<PostUploadResponse>>
}