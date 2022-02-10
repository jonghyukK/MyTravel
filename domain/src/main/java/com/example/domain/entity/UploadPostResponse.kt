package com.example.domain.entity

/**
 * MyTravel
 * Class: UploadPostResponse
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class UploadPostResponse(
    val result: Boolean,
    val data  : User?,
    val errorMsg: String? = null
)