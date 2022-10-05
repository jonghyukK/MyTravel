package org.kjh.mytravel.utils

import android.graphics.Color
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay

/**
 * MyTravel
 * Class: NaverMapUtils
 * Created by jonghyukkang on 2022/07/22.
 *
 * Description:
 */

object NaverMapUtils {

    fun getInactiveMarker(marker: Marker) = marker.apply { iconTintColor = Color.BLUE }

    fun getActiveMarker(marker: Marker) = marker.apply { iconTintColor = Color.RED }

    fun getCameraUpdateObject(
        x   : Double,
        y   : Double,
        zoom: Double? = null,
        anim: CameraAnimation = CameraAnimation.Easing
    ): CameraUpdate {
        val latLngItem = LatLng(y, x)

        return if (zoom != null) {
            CameraUpdate.scrollAndZoomTo(latLngItem, zoom).animate(anim)
        } else {
            CameraUpdate.scrollTo(latLngItem).animate(anim)
        }
    }

    fun makeMarker(
        x           : Double,
        y           : Double,
        captionTitle: String,
        callback    : ((Marker) -> Unit)? = null
    ): Marker =
        Marker().apply {
            position        = LatLng(y, x)
            captionText     = captionTitle
            iconTintColor   = Color.BLUE
            onClickListener = makeMarkerClickListener(callback)
        }

    private fun makeMarkerClickListener(callback: ((Marker) -> Unit)? = null) =
        Overlay.OnClickListener { overlay ->
            callback?.let {
                val selectedMarker = getActiveMarker(overlay as Marker)
                callback(selectedMarker)
                true
            } ?: false
        }
}