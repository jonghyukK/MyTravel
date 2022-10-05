package org.kjh.domain.entity

/**
 * MyTravel
 * Class: PlaceRanking
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */
data class PlaceRankingEntity(
    val rank     : Int,
    val placeId  : String,
    val placeImg : String,
    val placeName: String,
    val cityName : String,
)