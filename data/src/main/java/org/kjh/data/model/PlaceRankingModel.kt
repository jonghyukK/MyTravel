package org.kjh.data.model

import org.kjh.domain.entity.PlaceRankingEntity

/**
 * MyTravel
 * Class: PlaceWithRankModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class PlaceRankingModel(
    val rank : Int,
    val place: PlaceModel
)

fun PlaceRankingModel.mapToDomain() =
    PlaceRankingEntity(
        rank        = rank,
        placeId     = place.placeId,
        placeImg    = place.placeImg,
        placeName   = place.placeName,
        cityName    = place.cityName
    )