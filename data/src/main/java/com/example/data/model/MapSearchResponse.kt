package com.example.data.model

import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: MapSearchResponse
 * Created by jonghyukkang on 2022/01/28.
 *
 * Description:
 */
data class MapSearchResponse(
    @SerializedName("documents")
    val placeList: List<MapSearchModel>
) {
    data class MapSearchModel(
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
}