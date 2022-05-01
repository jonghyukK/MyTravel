package org.kjh.data.model

import org.kjh.domain.entity.MapQueryEntity
import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: MapQueryModel
 * Created by jonghyukkang on 2022/02/24.
 *
 * Description:
 */
data class MapQueryModel(
    @SerializedName("id")
    val placeId: String,

    @SerializedName("place_name")
    val placeName: String,

    @SerializedName("address_name")
    val placeAddress: String,

    @SerializedName("road_address_name")
    val placeRoadAddress: String,

    val x: String,

    val y: String
)

fun MapQueryModel.mapToDomain() =
    MapQueryEntity(placeId, placeName, placeAddress, placeRoadAddress, x, y)