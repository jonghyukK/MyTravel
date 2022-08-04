package org.kjh.data.model

import org.kjh.domain.entity.PlaceWithAroundEntity

/**
 * MyTravel
 * Class: PlaceWithArounds
 * Created by jonghyukkang on 2022/08/04.
 *
 * Description:
 */
data class PlaceWithAroundModel(
    val placeItem       : PlaceModel,
    val aroundPlaceItems: List<PlaceModel>
)

fun PlaceWithAroundModel.mapToDomain() =
    PlaceWithAroundEntity(
        placeItem.mapToDomain(),
        aroundPlaceItems.map { it.mapToDomain() }
    )