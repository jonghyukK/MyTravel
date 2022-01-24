package org.kjh.mytravel.data.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

/**
 * MyTravel
 * Class: KakaoSearchResponse
 * Created by jonghyukkang on 2022/01/24.
 *
 * Description:
 */
data class KakaoSearchResponse(
    @SerializedName("documents")
    val placeList: List<KakaoSearchPlaceModel>
)

data class KakaoSearchPlaceModel(

    @SerializedName("id")
    val placeId: String,

    @SerializedName("place_name")
    val placeName: String,

    @SerializedName("address_name")
    val addressName: String,

    @SerializedName("road_address_name")
    val roadAddressName: String,

    val x: String,

    val y: String
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<KakaoSearchPlaceModel>() {
            override fun areItemsTheSame(
                oldItem: KakaoSearchPlaceModel,
                newItem: KakaoSearchPlaceModel
            ): Boolean =
                oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: KakaoSearchPlaceModel,
                newItem: KakaoSearchPlaceModel
            ): Boolean =
                oldItem == newItem
        }
    }
}