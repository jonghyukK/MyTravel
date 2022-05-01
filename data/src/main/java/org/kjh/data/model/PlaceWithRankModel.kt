package org.kjh.data.model

import org.kjh.domain.entity.PlaceWithRankEntity

/**
 * MyTravel
 * Class: PlaceWithRankModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class PlaceWithRankModel(
    val rank : Int,
    val place: PlaceModel
)

fun PlaceWithRankModel.mapToDomain() =
    PlaceWithRankEntity(rank, place.mapToDomain())