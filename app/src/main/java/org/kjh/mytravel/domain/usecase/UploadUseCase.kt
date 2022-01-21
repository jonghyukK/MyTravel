package org.kjh.mytravel.domain.usecase

import org.kjh.mytravel.data.model.PostUploadModel
import org.kjh.mytravel.domain.repository.PostRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: UploadUseCase
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */
class UploadUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    fun execute(postUploadModel: PostUploadModel) =
        postRepository.upload(postUploadModel)
}