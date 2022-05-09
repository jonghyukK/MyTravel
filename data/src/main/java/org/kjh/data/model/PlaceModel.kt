package org.kjh.data.model

import org.kjh.domain.entity.PlaceEntity

/**
 * MyTravel
 * Class: PlaceModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */

data class PlaceModel(
    val placeId: String,
    val cityName: String,
    val subCityName: String,
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val x: String,
    val y: String,
    val placeImg: String,
    val posts : List<PostModel>
)

fun PlaceModel.mapToDomain() =
    PlaceEntity(
        placeId,
        cityName,
        subCityName,
        placeName,
        placeAddress,
        placeRoadAddress,
        x,
        y,
        placeImg,
        posts.map { it.mapToDomain() }
    )