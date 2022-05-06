package org.kjh.domain.usecase

import org.kjh.domain.repository.PostRepository

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
    operator fun invoke() = postRepository.fetchLatestPosts()
}