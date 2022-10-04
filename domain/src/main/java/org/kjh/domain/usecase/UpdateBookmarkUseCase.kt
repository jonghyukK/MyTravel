package org.kjh.domain.usecase

import org.kjh.domain.repository.UserRepository

/**
 * MyTravel
 * Class: GetBookmarksUseCase
 * Created by jonghyukkang on 2022/04/05.
 *
 * Description:
 */
class UpdateBookmarkUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(placeName: String) =
        userRepository.updateMyBookmarks(placeName)
}