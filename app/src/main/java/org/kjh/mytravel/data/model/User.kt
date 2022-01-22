package org.kjh.mytravel.data.model

import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: User
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */
data class User(
    val userId      : Int = 0,
    val email       : String = "",
    val nickName    : String = "",
    val profileImg  : String? = null,
    val postCount      : Int = 0,
    val followingCount : Int = 0,
    val followCount    : Int = 0,
    val introduce   : String? = null,
    val posts : List<Post> = listOf()
)

data class UserResponse(
    val result   : Boolean,

    @SerializedName("data")
    val userData : User,

    val errorMsg : String? = null
)