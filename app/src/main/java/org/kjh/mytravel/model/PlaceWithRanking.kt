package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import org.kjh.domain.entity.PlaceWithRankEntity

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
) {
    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<PlaceWithRanking>() {
            override fun areItemsTheSame(
                oldItem: PlaceWithRanking,
                newItem: PlaceWithRanking
            ) = oldItem.place.placeId == newItem.place.placeId

            override fun areContentsTheSame(
                oldItem: PlaceWithRanking,
                newItem: PlaceWithRanking
            ) = oldItem == newItem
        }
    }
}

fun PlaceWithRankEntity.mapToPresenter() =
    PlaceWithRanking(rank, place.mapToPresenter())