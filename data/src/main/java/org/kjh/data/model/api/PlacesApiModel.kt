package org.kjh.data.model.api

import org.kjh.data.model.PlaceModel

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