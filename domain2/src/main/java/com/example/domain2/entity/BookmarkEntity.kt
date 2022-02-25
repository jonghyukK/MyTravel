package com.example.domain2.entity

/**
 * MyTravel
 * Class: BookmarkEntity
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class BookmarkEntity(
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
    val isBookmarked: Boolean = false,
    val imageUrl    : List<String> = listOf()
)