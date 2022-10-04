package org.kjh.domain.entity

/**
 * MyTravel
 * Class: BookmarkEntity
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class BookmarkEntity(
    val cityName        : String,
    val subCityName     : String,
    val placeName       : String,
    val placeAddress    : String,
    val placeRoadAddress: String,
    val x               : String,
    val y               : String,
    val placeImg        : String,
    val isBookmarked    : Boolean
)