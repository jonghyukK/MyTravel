package org.kjh.mytravel.data.model

import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: LoginResponse
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
data class LoginResponse(

    @SerializedName("result")
    val isLoggedIn: Boolean,

    val errorMsg  : String? = null
)