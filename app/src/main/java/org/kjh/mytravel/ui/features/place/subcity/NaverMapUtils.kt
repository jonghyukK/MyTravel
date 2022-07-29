package org.kjh.mytravel.ui.features.place.subcity

import android.graphics.Color
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import org.kjh.mytravel.model.Place

/**
 * MyTravel
 * Class: NaverMapUtils
 * Created by jonghyukkang on 2022/07/22.
 *
 * Description:
 */
object NaverMapUtils {

    fun makeCameraMoveForCenterInRange(placeItems: List<Place>): CameraUpdate {
        val rightMost = placeItems.maxOf { place -> place.x }.toDouble()
        val leftMost = placeItems.minOf { place -> place.x }.toDouble()
        val centerOfHorizontal = (rightMost + leftMost) / 2

        val topMost = placeItems.minOf { it.y }.toDouble()
        val bottomMost = placeItems.maxOf { it.y }.toDouble()
        val centerOfVertical = (topMost + bottomMost) / 2

        return CameraUpdate.scrollAndZoomTo(
            LatLng(
                centerOfVertical,
                centerOfHorizontal,
            ), 9.0
        ).animate(CameraAnimation.None)
    }

    fun makeCameraMoveForOneMarker(placeItem: Place): CameraUpdate {
        return CameraUpdate.scrollTo(
            LatLng(
                placeItem.y.toDouble(),
                placeItem.x.toDouble()
            )
        ).animate(CameraAnimation.Easing)
    }

    fun makePlaceMapWithMarker(
        placeItems         : List<Place>,
        markerClickCallback: (Marker) -> Unit
    ): MutableMap<String, PlaceWithMarker> {
        val placeMarkerMap = mutableMapOf<String, PlaceWithMarker>()

        val markerClickListener = Overlay.OnClickListener { overlay ->
            val marker = getMarkerWithSelected(overlay as Marker)
            markerClickCallback(marker)
            true
        }

        placeItems.map { place ->
            val key    = place.placeName
            val marker = Marker().apply {
                position        = LatLng(place.y.toDouble(), place.x.toDouble())
                captionText     = place.placeName
                onClickListener = markerClickListener
                iconTintColor   = Color.BLUE
            }

            placeMarkerMap[key] = PlaceWithMarker(place, marker)
        }

        return placeMarkerMap
    }

    fun getMarkerWithSelected(marker: Marker) =
        marker.apply {
            iconTintColor = Color.RED
        }

    fun getMarkerWithUnSelected(marker: Marker) =
        marker.apply {
            iconTintColor = Color.BLUE
        }
}