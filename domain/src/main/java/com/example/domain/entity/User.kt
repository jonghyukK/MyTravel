package com.example.domain.entity

/**
 * MyTravel
 * Class: User
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

data class User(
    val userId: Int,
    val email : String,
    val nickName: String,
    val profileImg: String?,
    val postCount : Int,
    val followingCount : Int,
    val followCount: Int,
    val introduce : String?,
    val isFollowing: Boolean? = null,
    val posts: List<Post> = listOf()
)