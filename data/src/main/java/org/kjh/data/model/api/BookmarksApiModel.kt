package org.kjh.data.model.api

import org.kjh.data.model.BookmarkModel

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