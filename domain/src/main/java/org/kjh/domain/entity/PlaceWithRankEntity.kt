package org.kjh.domain.entity

/**
 * MyTravel
 * Class: PlaceRanking
 * Created by jonghyukkang on 2022/02/21.
 *
 * Description:
 */
data class PlaceWithRankEntity(
    val rank : Int,
    val place: PlaceEntity
)