package com.example.data.model.api

import com.example.data.model.PostModel

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