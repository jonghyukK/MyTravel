package org.kjh.mytravel.model

import com.example.domain2.entity.PlaceEntity
import com.example.domain2.entity.PlaceWithRankEntity

/**
 * MyTravel
 * Class: PlaceWithRanking
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class PlaceWithRanking(
    val rank : Int,
    val place: Place
)

fun PlaceWithRankEntity.mapToPresenter() =
    PlaceWithRanking(rank, place.mapToPresenter())