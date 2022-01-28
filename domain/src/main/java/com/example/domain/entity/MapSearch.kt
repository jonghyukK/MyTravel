package com.example.domain.entity

import androidx.recyclerview.widget.DiffUtil

/**
 * MyTravel
 * Class: MapSearch
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class MapSearch(
    val placeId: String,
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val x: String,
    val y: String
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MapSearch>() {
            override fun areItemsTheSame(
                oldItem: MapSearch,
                newItem: MapSearch
            ): Boolean =
                oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: MapSearch,
                newItem: MapSearch
            ): Boolean =
                oldItem == newItem
        }
    }
}