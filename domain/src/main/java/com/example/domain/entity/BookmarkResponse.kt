package com.example.domain.entity

/**
 * MyTravel
 * Class: BookmarkResponse
 * Created by jonghyukkang on 2022/02/09.
 *
 * Description:
 */
data class BookmarkResponse(
    val result: Boolean,
    val bookMarks: List<Post> = listOf(),
    val errorMsg: String? = null
)