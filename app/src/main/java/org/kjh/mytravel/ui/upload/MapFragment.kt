package org.kjh.mytravel.ui.upload

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.setupWithNavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.data.model.KakaoSearchPlaceModel
import org.kjh.mytravel.databinding.FragmentMapBinding
import org.kjh.mytravel.ui.base.BaseFragment


@AndroidEntryPoint
class MapFragment
    : BaseFragment<FragmentMapBinding>(R.layout.fragment_map), OnMapReadyCallback {

    private val uploadViewModel: UploadViewModel
            by navGraphViewModels(R.id.nav_nested_upload){ defaultViewModelProviderFactory }

    private val viewModel: MapViewModel by viewModels()
    private lateinit var naverMap: NaverMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithNavigation()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

        binding.btnOpenSearch.setOnClickListener {
            MapSearchFragment.newInstance().show(childFragmentManager, MapSearchFragment.TAG)
        }

        binding.btnAddPlaceInfo.setOnClickListener {
            uploadViewModel.updatePlaceItem(viewModel.tempSelectedPlaceItem.value!!)
            findNavController().popBackStack()
        }

        viewModel.tempSelectedPlaceItem.observe(viewLifecycleOwner, {
            binding.btnAddPlaceInfo.isVisible = it != null
            setMarketAtPlace(it)
        })
    }

    private fun setMarketAtPlace(placeItem: KakaoSearchPlaceModel?) {
        placeItem?.let {
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(placeItem.y.toDouble(), placeItem.x.toDouble()))
                .animate(CameraAnimation.Easing)
            naverMap.moveCamera(cameraUpdate)

            val marker = Marker()
            marker.position = LatLng(placeItem.y.toDouble(), placeItem.x.toDouble())
            marker.captionText = placeItem.placeName
            marker.map = naverMap
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbMapToolbar.apply {
            setupWithNavController(findNavController())
        }
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
    }
}