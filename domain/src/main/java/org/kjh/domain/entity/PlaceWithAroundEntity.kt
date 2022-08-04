package org.kjh.domain.entity

/**
 * MyTravel
 * Class: PlaceWithAroundEntity
 * Created by jonghyukkang on 2022/08/04.
 *
 * Description:
 */
data class PlaceWithAroundEntity(
    val placeItem       : PlaceEntity,
    val aroundPlaceItems: List<PlaceEntity>
)