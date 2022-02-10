package com.example.domain.usecase

import com.example.domain.repository.PostRepository
import com.example.domain.repository.UserRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: UploadPostUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class UploadPostUseCase @Inject constructor(
    private val postRepository: PostRepository
){
    suspend operator fun invoke(
        file: List<String>,
        email: String,
        content: String?,
        placeName: String,
        placeAddress: String,
        placeRoadAddress: String,
        x: String,
        y: String
    ) = postRepository.makeRequestUploadPost(file, email, content, placeName, placeAddress, placeRoadAddress, x, y)
}