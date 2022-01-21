package org.kjh.mytravel.data.model

/**
 * MyTravel
 * Class: PostUploadModel
 * Created by jonghyukkang on 2022/01/21.
 *
 * Description:
 */
data class PostUploadModel(
    val email       : String,
    val content     : String? = null,
    val cityName    : String,
    val placeName   : String,
    val placeAddress: String,
)

data class PostUploadResponse(
    val result: String,
    val errorMsg: String? = null
)