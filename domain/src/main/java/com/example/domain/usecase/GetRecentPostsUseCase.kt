package com.example.domain.usecase

import com.example.domain.repository.PostRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: GetRecentPostsUseCase
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */
class GetRecentPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
){
    operator fun invoke(myEmail: String) = postRepository.getRecentPostsPagingData(myEmail)
}