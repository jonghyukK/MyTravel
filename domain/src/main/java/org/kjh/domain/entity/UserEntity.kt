package org.kjh.domain.entity

/**
 * MyTravel
 * Class: UserEntity
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */

data class UserEntity(
    val userId      : Int = 0,
    val email       : String = "",
    val nickName    : String = "",
    val profileImg  : String? = null,
    val postCount   : Int = 0,
    val followingCount : Int = 0,
    val followCount    : Int = 0,
    val introduce      : String? = null,
    val isFollowing    : Boolean = false,
    val posts          : List<PostEntity> = listOf(),
    val bookMarks      : List<BookmarkEntity> = listOf()
)