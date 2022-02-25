package com.example.data.model.api

import com.example.data.model.PlaceModel

/**
 * MyTravel
 * Class: PlaceModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class PlaceApiModel(
    val result  : Boolean,
    val data    : PlaceModel,
    val errorMsg: String? = null
)
