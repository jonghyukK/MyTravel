package org.kjh.mytravel.model

import org.kjh.domain.entity.LatestDayLogEntity

/**
 * MyTravel
 * Class: LatestDayLogItemUiState
 * Created by jonghyukkang on 2022/09/14.
 *
 * Description:
 */
data class LatestDayLogItemUiState(
    val dayLogId    : Int,
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
    val isMyDayLog  : Boolean,
    val onBookmark  : () -> Unit,
    val onDeleteDayLog: () -> Unit
)

fun LatestDayLogEntity.mapToPresenter(
    onBookmarkAction    : () -> Unit,
    onDeleteDayLogAction: () -> Unit
) = LatestDayLogItemUiState(
        dayLogId     = dayLogId,
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
        isMyDayLog   = isMyDayLog,
        onBookmark   = onBookmarkAction,
        onDeleteDayLog = onDeleteDayLogAction
    )