package org.kjh.mytravel.domain.usecase

import okhttp3.MultipartBody
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
    private val postRepository: PostRepository,
    private val getLoginInfoUseCase: GetLoginInfoUseCase
) {
    suspend operator fun invoke(
        file: List<MultipartBody.Part>,
        content: String?,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ) = postRepository.makeRequestPostUpload(
        file = file,
        email = getLoginInfoUseCase(),
        content = content,
        placeName = placeName,
        placeAddress = placeAddress,
        placeRoadAddress = placeRoadAddress,
        x = x,
        y = y
    )
}