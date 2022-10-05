package org.kjh.mytravel.model

import com.naver.maps.map.overlay.Marker

/**
 * MyTravel
 * Class: PlaceWithMarker
 * Created by jonghyukkang on 2022/07/29.
 *
 * Description:
 */
data class PlaceWithMarker(
    val placeItem  : Place,
    val placeMarker: Marker
)
