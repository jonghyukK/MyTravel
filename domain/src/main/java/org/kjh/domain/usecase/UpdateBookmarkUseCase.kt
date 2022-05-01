package org.kjh.domain.usecase

import org.kjh.domain.repository.BookmarkRepository

/**
 * MyTravel
 * Class: GetBookmarksUseCase
 * Created by jonghyukkang on 2022/04/05.
 *
 * Description:
 */
class UpdateBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(
        myEmail: String,
        postId: Int,
        placeName: String
    ) = bookmarkRepository.updateBookmark(myEmail, postId, placeName)
}