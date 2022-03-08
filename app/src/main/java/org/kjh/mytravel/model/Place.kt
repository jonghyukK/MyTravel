package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import com.example.domain2.entity.PlaceEntity

/**
 * MyTravel
 * Class: Place
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class Place(
    val placeId         : String,
    val cityName        : String,
    val subCityName     : String,
    val placeName       : String,
    val placeAddress    : String,
    val placeRoadAddress: String,
    val x       : String,
    val y       : String,
    val placeImg: String,
    val posts   : List<Post> = listOf()
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(
                oldItem: Place,
                newItem: Place
            ): Boolean =
                oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: Place,
                newItem: Place
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun PlaceEntity.mapToPresenter() =
    Place(placeId,
        cityName,
        subCityName,
        placeName,
        placeAddress,
        placeRoadAddress,
        x,
        y,
        placeImg,
        posts.map { it.mapToPresenter() }
    )