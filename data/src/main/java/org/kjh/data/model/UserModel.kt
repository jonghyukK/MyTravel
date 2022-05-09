package org.kjh.data.model

import org.kjh.domain.entity.UserEntity

/**
 * MyTravel
 * Class: UserModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */

data class UserModel(
    val userId        : Int,
    val email         : String,
    val nickName      : String,
    val profileImg    : String,
    val postCount     : Int,
    val followingCount: Int,
    val followCount   : Int,
    val introduce     : String,
    val isFollowing   : Boolean,
    val posts         : List<PostModel>,
    val bookMarks     : List<BookmarkModel>
)

fun UserModel.mapToDomain() =
    UserEntity(
        userId,
        email,
        nickName,
        profileImg,
        postCount,
        followingCount,
        followCount,
        introduce,
        isFollowing,
        posts.map { it.mapToDomain() },
        bookMarks.map { it.mapToDomain() }
    )