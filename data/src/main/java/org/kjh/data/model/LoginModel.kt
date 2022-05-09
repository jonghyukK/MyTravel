package org.kjh.data.model

import org.kjh.domain.entity.LoginEntity

/**
 * MyTravel
 * Class: LoginModel
 * Created by jonghyukkang on 2022/05/10.
 *
 * Description:
 */
data class LoginModel(
    val isSuccess    : Boolean,
    val loginErrorMsg: String?
)

fun LoginModel.mapToDomain() =
    LoginEntity(isSuccess, loginErrorMsg)