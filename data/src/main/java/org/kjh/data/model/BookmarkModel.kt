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
    val postId      : Int,
    val email       : String,
    val nickName    : String,
    val content     : String?,
    val cityName    : String,
    val subCityName : String,
    val placeName   : String,
    val placeAddress: String,
    val profileImg  : String?,
    val createdDate : String,
    val isBookmarked: Boolean,
    val imageUrl    : List<String>
)

fun BookmarkModel.mapToDomain() =
    BookmarkEntity(
        postId,
        email,
        nickName,
        content,
        cityName,
        subCityName,
        placeName,
        placeAddress,
        profileImg,
        createdDate,
        isBookmarked,
        imageUrl
    )