package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import org.kjh.domain.entity.BookmarkEntity

/**
 * MyTravel
 * Class: Bookmark
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class Bookmark(
    val cityName        : String,
    val subCityName     : String,
    val placeName       : String,
    val placeAddress    : String,
    val placeRoadAddress: String,
    val x               : String,
    val y               : String,
    val placeImg        : String,
    val isBookmarked    : Boolean
){
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Bookmark>() {
            override fun areItemsTheSame(
                oldItem: Bookmark,
                newItem: Bookmark
            ): Boolean =
                oldItem.placeName == newItem.placeName

            override fun areContentsTheSame(
                oldItem: Bookmark,
                newItem: Bookmark
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun BookmarkEntity.mapToPresenter() =
    Bookmark(
        cityName,
        subCityName,
        placeName,
        placeAddress,
        placeRoadAddress,
        x,
        y,
        placeImg,
        isBookmarked
    )

fun List<Bookmark>.isBookmarkedPlace(placeName: String) =
    this.let { bookmarkList ->
        bookmarkList.find { it.placeName == placeName } != null
    }
