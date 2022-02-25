package com.example.domain2.usecase

import com.example.domain2.repository.UserRepository

/**
 * MyTravel
 * Class: UpdateBookMarkUseCase
 * Created by jonghyukkang on 2022/02/08.
 *
 * Description:
 */
class UpdateBookMarkUseCase(
    private val userRepository: UserRepository,
    private val loginPreferenceUseCase: GetLoginPreferenceUseCase
){
    suspend operator fun invoke(postId: Int) =
        userRepository.updateBookmark(loginPreferenceUseCase().email, postId)
}