package com.example.domain.entity

/**
 * MyTravel
 * Class: Login
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class Login(
    val isLoggedIn: Boolean,
    val errorMsg  : String? = null,
)