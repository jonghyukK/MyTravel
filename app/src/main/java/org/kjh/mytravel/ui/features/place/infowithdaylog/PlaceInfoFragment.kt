package org.kjh.mytravel.ui.features.place.infowithdaylog

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceInfoBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.ui.base.BaseMapFragment


class PlaceInfoFragment
    : BaseMapFragment<FragmentPlaceInfoBinding>(R.layout.fragment_place_info) {

    private val viewModel: PlaceInfoWithDayLogViewModel by viewModels({ requireParentFragment() })

    override fun initView() {
        binding.viewModel = viewModel

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_placeInfo) as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun subscribeUi() {
        viewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.RESUMED)
            .onEach { uiState ->
                uiState.markerItem?.let {
                    it.placeMarker.map = naverMap
                }

                uiState.cameraMoveEvent?.let {
                    naverMap.moveCamera(it)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearMarkerAndCameraMoveEvent()
    }

    override fun naverMapClickEvent() {}

    companion object {
        @JvmStatic
        fun newInstance() = PlaceInfoFragment()
    }
}