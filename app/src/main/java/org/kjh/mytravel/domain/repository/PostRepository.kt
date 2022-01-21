package org.kjh.mytravel.domain.repository

import kotlinx.coroutines.flow.Flow
import org.kjh.mytravel.data.model.PostUploadModel
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
    fun upload(postUploadModel: PostUploadModel): Flow<Result<PostUploadResponse>>
}