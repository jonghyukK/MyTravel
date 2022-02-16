package com.example.domain.entity

/**
 * MyTravel
 * Class: User
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */

data class User(
    val userId      : Int = 0,
    val email       : String = "",
    val nickName    : String = "",
    val profileImg  : String? = null,
    val postCount   : Int = 0,
    val followingCount : Int = 0,
    val followCount    : Int = 0,
    val introduce      : String? = null,
    val isFollowing    : Boolean? = null,
    val posts          : List<Post> = listOf(),
    val bookMarks      : List<Post> = listOf()
)