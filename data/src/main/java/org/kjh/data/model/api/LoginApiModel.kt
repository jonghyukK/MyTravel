package org.kjh.data.model.api

import org.kjh.domain.entity.LoginEntity
import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: LoginResonse
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class LoginApiModel(
    @SerializedName("result")
    val isLoggedIn: Boolean,
    val errorMsg  : String? = null
)

fun LoginApiModel.mapToDomain() =
    LoginEntity(isLoggedIn, errorMsg)