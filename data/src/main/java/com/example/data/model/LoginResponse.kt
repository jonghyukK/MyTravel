package com.example.data.model

import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: LoginResonse
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class LoginResponse(
    @SerializedName("result")
    val isLoggedIn: Boolean,
    val errorMsg  : String? = null
)