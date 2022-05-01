package org.kjh.data.model.api

import org.kjh.data.model.PlaceWithRankModel

/**
 * MyTravel
 * Class: PlaceRankingApiModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class PlaceRankingApiModel(
    val result  : Boolean,
    val data    : List<PlaceWithRankModel>,
    val errorMsg: String? = null
)