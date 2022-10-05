package org.kjh.domain.entity

/**
 * MyTravel
 * Class: UserEntity
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */

data class UserEntity(
    val userId      : Int,
    val email       : String,
    val nickName    : String,
    val profileImg  : String?,
    val postCount   : Int,
    val followingCount : Int,
    val followCount    : Int,
    val introduce      : String?,
    val isFollowing    : Boolean,
    val dayLogs        : List<DayLogEntity>,
    val bookMarks      : List<BookmarkEntity>
)