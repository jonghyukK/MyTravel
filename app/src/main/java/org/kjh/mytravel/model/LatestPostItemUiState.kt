package org.kjh.mytravel.model

import org.kjh.data.Event
import org.kjh.domain.entity.LatestPostEntity

/**
 * MyTravel
 * Class: LatestPostItemUiState
 * Created by jonghyukkang on 2022/09/14.
 *
 * Description:
 */
data class LatestPostItemUiState(
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
    val imageUrl    : List<String>,
    val isMyPost    : Boolean,
    val onBookmark  : () -> Unit,
    val onDeleteDayLog: () -> Unit
)

fun LatestPostEntity.mapToPresenter(
    onBookmarkAction    : () -> Unit,
    onDeleteDayLogAction: () -> Unit
) = LatestPostItemUiState(
        postId       = postId,
        email        = email,
        nickName     = nickName,
        content      = content,
        cityName     = cityName,
        subCityName  = subCityName,
        placeName    = placeName,
        placeAddress = placeAddress,
        profileImg   = profileImg,
        createdDate  = createdDate,
        imageUrl     = imageUrl,
        isBookmarked = isBookmarked,
        isMyPost     = isMyPost,
        onBookmark   = onBookmarkAction,
        onDeleteDayLog = onDeleteDayLogAction
    )