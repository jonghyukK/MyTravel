package com.example.data.model

import com.example.domain.entity.User
import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: SignUpResponse
 * Created by jonghyukkang on 2022/01/27.
 *
 * Description:
 */
data class SignUpResponse(
    @SerializedName("result")
    val isRegistered: Boolean,
    val data        : User? = null,
    val errorMsg    : String? = null
)