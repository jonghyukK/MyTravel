package com.example.domain.entity

/**
 * MyTravel
 * Class: Login
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class Login(
    val result  : Boolean,
    val data    : User?,
    val errorMsg: String? = null,
)