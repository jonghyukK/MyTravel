package org.kjh.data.model

import org.kjh.domain.entity.PostEntity

/**
 * MyTravel
 * Class: PostModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class PostModel(
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

fun PostModel.mapToDomain() =
    PostEntity(
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