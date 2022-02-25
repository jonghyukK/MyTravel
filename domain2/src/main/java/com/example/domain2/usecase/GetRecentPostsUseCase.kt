package com.example.domain2.usecase

import com.example.domain2.repository.PostRepository

/**
 * MyTravel
 * Class: GetRecentPostsUseCase
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */
class GetRecentPostsUseCase(
    private val postRepository: PostRepository
){
    operator fun invoke(myEmail: String) = postRepository.getRecentPostsPagingData(myEmail)
}