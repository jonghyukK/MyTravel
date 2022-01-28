package com.example.domain.entity

/**
 * MyTravel
 * Class: Place
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class Place(
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val cityName: String,
    val subCityName: String,
    val x: String,
    val y: String,
    val posts : List<Post>
)