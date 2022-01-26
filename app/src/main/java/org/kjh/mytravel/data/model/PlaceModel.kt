package org.kjh.mytravel.data.model

/**
 * MyTravel
 * Class: PlaceModel
 * Created by mac on 2022/01/18.
 *
 * Description:
 */
data class PlaceModel(
    val placeName: String,
    val placeAddress: String,
    val placeRoadAddress: String,
    val cityName: String,
    val x: String,
    val y: String,
    val posts : List<Post>
)

data class PlaceResponse(
    val result: Boolean,
    val data: PlaceModel,
    val errorMsg: String? = null
)