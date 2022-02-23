package com.example.domain.entity

/**
 * MyTravel
 * Class: Place
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class Place(
    val placeId: String,
    val cityName: String,
    val subCityName: String,
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val x: String,
    val y: String,
    val placeImg: String,
    val posts : List<Post> = listOf()
)