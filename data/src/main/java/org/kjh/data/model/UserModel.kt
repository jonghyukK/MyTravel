package org.kjh.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.kjh.domain.entity.UserEntity

/**
 * MyTravel
 * Class: UserModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */

@Entity(tableName = "user")
data class UserModel(
    @PrimaryKey
    val email         : String,
    val userId        : Int,
    val nickName      : String,
    val profileImg    : String?,
    val postCount     : Int,
    val followingCount: Int,
    val followCount   : Int,
    val introduce     : String?,
    val isFollowing   : Boolean,
    @SerializedName("posts")
    val dayLogs       : List<DayLogModel>,
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
        dayLogs.map { it.mapToDomain() },
        bookMarks.map { it.mapToDomain() }
    )