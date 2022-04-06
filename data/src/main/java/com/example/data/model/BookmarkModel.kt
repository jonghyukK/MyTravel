package com.example.data.model

import com.example.domain2.entity.BookmarkEntity

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
    val content     : String? = null,
    val cityName    : String,
    val subCityName : String,
    val placeName   : String,
    val placeAddress: String,
    val profileImg  : String? = null,
    val createdDate : String,
    val isBookmarked: Boolean = true,
    val imageUrl    : List<String> = listOf()
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