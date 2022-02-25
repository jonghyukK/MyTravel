package org.kjh.mytravel.model

import com.example.domain2.entity.LoginEntity

/**
 * MyTravel
 * Class: Login
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class Login(
    val isLoggedIn: Boolean,
    val errorMsg  : String? = null,
)

fun LoginEntity.mapToPresenter() =
    Login(isLoggedIn, errorMsg)