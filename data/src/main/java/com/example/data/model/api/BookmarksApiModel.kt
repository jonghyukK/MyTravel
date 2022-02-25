package com.example.data.model.api

import com.example.data.model.BookmarkModel

/**
 * MyTravel
 * Class: BookmarkApiModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class BookmarksApiModel(
    val result  : Boolean,
    val data    : List<BookmarkModel>,
    val errorMsg: String? = null
)