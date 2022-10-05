package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import org.kjh.domain.entity.PlaceRankingEntity

/**
 * MyTravel
 * Class: PlaceWithRanking
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class PlaceRankingItemUiState(
    val rank     : Int,
    val placeId  : String,
    val placeImg : String,
    val placeName: String,
    val cityName : String,
) {
    companion object {
        val diffCallback = object: DiffUtil.ItemCallback<PlaceRankingItemUiState>() {
            override fun areItemsTheSame(
                oldItem: PlaceRankingItemUiState,
                newItem: PlaceRankingItemUiState
            ) = oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: PlaceRankingItemUiState,
                newItem: PlaceRankingItemUiState
            ) = oldItem == newItem
        }
    }
}

fun PlaceRankingEntity.mapToPresenter() =
    PlaceRankingItemUiState(
        rank        = rank,
        placeId     = placeId,
        placeImg    = placeImg,
        placeName   = placeName,
        cityName    = cityName
    )