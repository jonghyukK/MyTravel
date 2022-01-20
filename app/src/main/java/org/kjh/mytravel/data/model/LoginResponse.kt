package org.kjh.mytravel.data.model

/**
 * MyTravel
 * Class: LoginResponse
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
data class LoginResponse(
    val isLoggedIn: Boolean,
    val errorMsg: String? = null
)