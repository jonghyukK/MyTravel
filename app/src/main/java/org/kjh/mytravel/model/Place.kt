package org.kjh.mytravel.model

import android.graphics.Camera
import android.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import org.kjh.domain.entity.PlaceEntity
import org.kjh.mytravel.ui.features.place.subcity.NaverMapUtils

/**
 * MyTravel
 * Class: Place
 * Created by jonghyukkang on 2022/02/25.
 *
 * Description:
 */
data class Place(
    val placeId         : String,
    val cityName        : String,
    val subCityName     : String,
    val placeName       : String,
    val placeAddress    : String,
    val placeRoadAddress: String,
    val x       : String,
    val y       : String,
    val placeImg: String,
    val posts   : List<Post>
) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(
                oldItem: Place,
                newItem: Place
            ): Boolean =
                oldItem.placeId == newItem.placeId

            override fun areContentsTheSame(
                oldItem: Place,
                newItem: Place
            ): Boolean =
                oldItem == newItem
        }
    }
}

fun PlaceEntity.mapToPresenter() =
    Place(placeId,
        cityName,
        subCityName,
        placeName,
        placeAddress,
        placeRoadAddress,
        x,
        y,
        placeImg,
        posts.map { it.mapToPresenter() }
    )

fun Place.withMarker(markerClickCallback: ((Marker) -> Unit)? = null): PlaceWithMarker =
    PlaceWithMarker(
        placeItem   = this,
        placeMarker = NaverMapUtils.makeMarker(
            x            = this.x.toDouble(),
            y            = this.y.toDouble(),
            captionTitle = this.placeName,
            callback     = markerClickCallback
        )
    )

fun List<Place>.withMarkerToMap(
    markerClickCallback: ((Marker) -> Unit)? = null
): Map<String, PlaceWithMarker> =
    this.associate {
        it.placeName to it.withMarker(markerClickCallback)
    }

fun Place.cameraMove(anim: CameraAnimation = CameraAnimation.Easing): CameraUpdate =
    NaverMapUtils.getCameraUpdateObject(
        x = this.x.toDouble(),
        y = this.y.toDouble(),
        anim = anim
    )

fun List<Place>.cameraCenterInPlaces(): CameraUpdate {
    val rightMost = this.maxOf { it.x }.toDouble()
    val leftMost  = this.minOf { it.x }.toDouble()
    val centerOfHorizontal = (rightMost + leftMost) / 2

    val topMost    = this.minOf { it.y }.toDouble()
    val bottomMost = this.maxOf { it.y }.toDouble()
    val centerOfVertical = (topMost + bottomMost) / 2

    return NaverMapUtils.getCameraUpdateObject(
        x = centerOfHorizontal,
        y = centerOfVertical,
        zoom = 9.0,
        anim = CameraAnimation.None
    )
}