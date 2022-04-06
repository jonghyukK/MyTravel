package com.example.domain2.usecase

import com.example.domain2.repository.BookmarkRepository

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