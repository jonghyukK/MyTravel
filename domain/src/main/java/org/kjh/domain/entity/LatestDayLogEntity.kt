package org.kjh.domain.entity

/**
 * MyTravel
 * Class: LatestDayLogEntity
 * Created by jonghyukkang on 2022/09/14.
 *
 * Description:
 */
data class LatestDayLogEntity(
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
    val isMyDayLog  : Boolean
)