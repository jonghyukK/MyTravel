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
    val userId      : Int,
    val email       : String,
    val nickName    : String,
    val profileImg  : String?,
    val postCount   : Int,
    val followingCount : Int,
    val followCount    : Int,
    val introduce      : String?,
    val isFollowing    : Boolean,
    val posts          : List<Post>,
    val bookMarks      : List<Bookmark>
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