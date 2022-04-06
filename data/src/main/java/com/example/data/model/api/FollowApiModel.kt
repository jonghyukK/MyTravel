package com.example.data.model.api

import com.example.data.model.FollowModel

/**
 * MyTravel
 * Class: FollowApiModel
 * Created by jonghyukkang on 2022/04/04.
 *
 * Description:
 */
data class FollowApiModel(
    val result  : Boolean,
    val data    : FollowModel,
    val errorMsg: String? = null
)