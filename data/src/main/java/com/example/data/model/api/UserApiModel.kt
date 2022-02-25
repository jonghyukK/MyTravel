package com.example.data.model.api

import com.example.data.model.UserModel

/**
 * MyTravel
 * Class: UserModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class UserApiModel(
    val result  : Boolean,
    val data    : UserModel,
    val errorMsg: String? = null
)