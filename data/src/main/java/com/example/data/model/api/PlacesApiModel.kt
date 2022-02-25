package com.example.data.model.api

import com.example.data.model.PlaceModel

/**
 * MyTravel
 * Class: PlacesApiModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class PlacesApiModel(
    val result  : Boolean,
    val data    : List<PlaceModel>,
    val errorMsg: String? = null
)