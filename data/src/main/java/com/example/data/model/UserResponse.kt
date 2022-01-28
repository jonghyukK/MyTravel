package com.example.data.model

import com.example.domain.entity.User

/**
 * MyTravel
 * Class: UserResponse
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
class UserResponse(
    val result  : Boolean,
    val data    : User,
    val errorMsg: String? = null
)