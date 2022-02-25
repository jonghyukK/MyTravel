package com.example.domain2.usecase

import com.example.domain2.repository.PostRepository

/**
 * MyTravel
 * Class: UploadPostUseCase
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class UploadPostUseCase(
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