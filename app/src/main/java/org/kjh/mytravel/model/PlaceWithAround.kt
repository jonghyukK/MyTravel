package org.kjh.mytravel.model

import org.kjh.domain.entity.PlaceWithAroundEntity

/**
 * MyTravel
 * Class: PlaceWithAround
 * Created by jonghyukkang on 2022/08/04.
 *
 * Description:
 */
data class PlaceWithAround(
    val placeItem       : Place,
    val aroundPlaceItems: List<Place>
)

fun PlaceWithAroundEntity.mapToPresenter() =
    PlaceWithAround(
        placeItem = placeItem.mapToPresenter(),
        aroundPlaceItems = aroundPlaceItems.map { it.mapToPresenter() }
    )