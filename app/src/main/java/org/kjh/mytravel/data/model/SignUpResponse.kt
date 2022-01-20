package org.kjh.mytravel.data.model

/**
 * MyTravel
 * Class: SignUpResponse
 * Created by mac on 2022/01/18.
 *
 * Description:
 */

data class SignUpResponse(
    val isRegistered: Boolean,
    val errorMsg    : String? = null
)