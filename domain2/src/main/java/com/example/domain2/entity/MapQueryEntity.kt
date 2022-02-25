package com.example.domain2.entity

/**
 * MyTravel
 * Class: MapSearch
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class MapQueryEntity(
    val placeId         : String,
    val placeName       : String,
    val placeAddress    : String,
    val placeRoadAddress: String,
    val x               : String,
    val y               : String
)