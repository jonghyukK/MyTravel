package com.example.data.model

import com.example.domain.entity.Place

/**
 * MyTravel
 * Class: PlaceResponse
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class PlaceResponse(
    val result: Boolean,
    val data: Place,
    val errorMsg: String? = null
)