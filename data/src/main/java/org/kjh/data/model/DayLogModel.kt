package org.kjh.data.model

import com.google.gson.annotations.SerializedName
import org.kjh.domain.entity.DayLogEntity

/**
 * MyTravel
 * Class: DayLogModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class DayLogModel(
    @SerializedName("postId")
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

fun DayLogModel.mapToDomain() =
    DayLogEntity(
        dayLogId,
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