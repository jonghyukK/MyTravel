package org.kjh.data.model

import org.kjh.domain.entity.BookmarkEntity

/**
 * MyTravel
 * Class: BookmarkModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class BookmarkModel(
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

fun BookmarkModel.mapToDomain() =
    BookmarkEntity(
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