package org.kjh.mytravel.ui.features.place.infowithdaylog

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceInfoBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.ui.base.BaseMapFragment


class PlaceInfoFragment
    : BaseMapFragment<FragmentPlaceInfoBinding>(R.layout.fragment_place_info) {

    private val viewModel: PlaceInfoWithDayLogViewModel by viewModels({ requireParentFragment() })
    private lateinit var marker  : Marker

    override fun initView() {
        binding.viewModel = viewModel

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_placeInfo) as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun subscribeUi() {
        viewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                uiState.placeItem?.let {
                    if (!::marker.isInitialized) {
                        makeMarkerWithCameraMove(uiState.placeItem)
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun makeMarkerWithCameraMove(placeItem: Place) {
        val cameraUpdate = CameraUpdate.scrollTo(
            LatLng(
                placeItem.y.toDouble(),
                placeItem.x.toDouble())
        ).animate(CameraAnimation.Easing)

        naverMap.moveCamera(cameraUpdate)

        marker = Marker().apply {
            position = LatLng(placeItem.y.toDouble(), placeItem.x.toDouble())
            captionText = placeItem.placeName
        }
        marker.map = naverMap
    }

    override fun onResume() {
        super.onResume()
        if (::marker.isInitialized && marker.map == null) {
            marker.map = naverMap
        }
    }

    override fun onPause() {
        super.onPause()
        marker.map = null
    }

    override fun naverMapClickEvent() {}

    companion object {
        @JvmStatic
        fun newInstance() = PlaceInfoFragment()
    }
}