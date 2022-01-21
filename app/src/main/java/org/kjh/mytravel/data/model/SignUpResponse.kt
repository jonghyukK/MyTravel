package org.kjh.mytravel.data.model

import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: SignUpResponse
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

data class SignUpResponse(

    @SerializedName("result")
    val isRegistered: Boolean,
    val errorMsg    : String? = null
)