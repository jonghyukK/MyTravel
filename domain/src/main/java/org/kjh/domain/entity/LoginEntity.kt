package org.kjh.domain.entity

/**
 * MyTravel
 * Class: Login
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class LoginEntity(
    val isSuccess     : Boolean,
    val loginErrorMsg : String?,
)