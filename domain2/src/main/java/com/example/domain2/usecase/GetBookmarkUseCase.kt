package com.example.domain2.usecase

import com.example.domain2.repository.BookmarkRepository

/**
 * MyTravel
 * Class: GetBookmarkUseCase
 * Created by jonghyukkang on 2022/04/05.
 *
 * Description:
 */
class GetBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(myEmail: String) =
        bookmarkRepository.getMyBookmarkList(myEmail)
}