package org.kjh.data.model.api

import org.kjh.data.model.PostModel

/**
 * MyTravel
 * Class: PostModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class PostsApiModel(
    val result  : Boolean,
    val data    : List<PostModel>,
    val errorMsg: String? = null
)