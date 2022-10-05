package org.kjh.mytravel.model

import androidx.recyclerview.widget.DiffUtil
import org.kjh.domain.entity.DayLogEntity

/**
 * MyTravel
 * Class: DayLog
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class DayLog(
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
    var isBookmarked: Boolean,
    val imageUrl    : List<String>
){
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<DayLog>() {
            override fun areItemsTheSame(
                oldItem: DayLog,
                newItem: DayLog
            ) = oldItem.dayLogId == newItem.dayLogId &&
                    oldItem.isBookmarked == newItem.isBookmarked

            override fun areContentsTheSame(
                oldItem: DayLog,
                newItem: DayLog
            ) = oldItem == newItem
        }
    }
}

fun DayLogEntity.mapToPresenter() =
    DayLog(
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
