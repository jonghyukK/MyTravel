package com.example.domain.entity

/**
 * MyTravel
 * Class: RecentPostsResponse
 * Created by jonghyukkang on 2022/02/16.
 *
 * Description:
 */
data class RecentPostsResponse(
    val result: Boolean,
    val data  : List<Post>,
    val errorMsg: String? = null
)