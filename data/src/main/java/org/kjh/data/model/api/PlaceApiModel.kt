package org.kjh.data.model.api

import org.kjh.data.model.PlaceModel

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
