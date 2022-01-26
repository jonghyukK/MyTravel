package org.kjh.mytravel.ui.place

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceInfoBinding
import org.kjh.mytravel.ui.base.BaseFragment


class PlaceInfoFragment
    : BaseFragment<FragmentPlaceInfoBinding>(R.layout.fragment_place_info), OnMapReadyCallback {

    private val viewModel: PlaceViewModel by viewModels({ requireParentFragment() })

    private lateinit var naverMap: NaverMap
    private lateinit var marker: Marker

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initMapFragment()
    }

    private fun initMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_placeInfo) as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->

                    if (uiState.x.isNotBlank() && uiState.y.isNotBlank() && !::marker.isInitialized) {
                        setMarketAtPlace(uiState)
                    }
                }
            }
        }
    }

    private fun setMarketAtPlace(uiState: PlaceUiState) {
        val cameraUpdate =
            CameraUpdate.scrollTo(LatLng(uiState.y.toDouble(), uiState.x.toDouble()))
                .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)

        marker = Marker().apply {
            position = LatLng(uiState.y.toDouble(), uiState.x.toDouble())
            captionText = uiState.placeName
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

    companion object {
        @JvmStatic
        fun newInstance() = PlaceInfoFragment()
    }
}