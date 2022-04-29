package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import com.example.domain2.entity.MapQueryEntity

/**
 * MyTravel
 * Class: MapQuery
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class MapQueryItem(
    val placeId         : String,
    val placeName       : String,
    val placeAddress    : String,
    val placeRoadAddress: String,
    val x               : String,
    val y               : String
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<MapQueryItem>() {
            override fun areItemsTheSame(
                oldItem: MapQueryItem,
                newItem: MapQueryItem
            ): Boolean =
                oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: MapQueryItem,
                newItem: MapQueryItem
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun MapQueryEntity.mapToPresenter() =
    MapQueryItem(placeId, placeName, placeAddress, placeRoadAddress, x, y)