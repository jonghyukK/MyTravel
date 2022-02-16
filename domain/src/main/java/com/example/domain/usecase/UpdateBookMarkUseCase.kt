package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import javax.inject.Inject

/**
 * MyTravel
 * Class: UpdateBookMarkUseCase
 * Created by jonghyukkang on 2022/02/08.
 *
 * Description:
 */
class UpdateBookMarkUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val loginPreferenceUseCase: GetLoginPreferenceUseCase
){
    suspend operator fun invoke(postId: Int) =
        userRepository.updateBookmark(loginPreferenceUseCase().email, postId)
}