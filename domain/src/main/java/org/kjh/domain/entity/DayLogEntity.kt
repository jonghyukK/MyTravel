package org.kjh.domain.entity

/**
 * MyTravel
 * Class: PostEntity
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class DayLogEntity(
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
    val imageUrl    : List<String>
)