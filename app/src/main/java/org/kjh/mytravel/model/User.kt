package org.kjh.mytravel.model

import org.kjh.domain.entity.UserEntity

/**
 * MyTravel
 * Class: User
 * Created by jonghyukkang on 2022/02/25.
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
    val isFollowing    : Boolean = false,
    val posts          : List<Post> = listOf(),
    val bookMarks      : List<Bookmark> = listOf()
)

fun UserEntity.mapToPresenter() =
    User(
        userId,
        email,
        nickName,
        profileImg,
        postCount,
        followingCount,
        followCount,
        introduce,
        isFollowing,
        posts.map { it.mapToPresenter() },
        bookMarks.map { it.mapToPresenter()}
    )