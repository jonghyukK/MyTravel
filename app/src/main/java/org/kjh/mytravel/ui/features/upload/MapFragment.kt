package org.kjh.mytravel.ui.features.upload

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.setupWithNavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentMapBinding
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.ui.base.BaseFragment

@AndroidEntryPoint
class MapFragment
    : BaseFragment<FragmentMapBinding>(R.layout.fragment_map),
    OnMapReadyCallback
{
    private lateinit var naverMap: NaverMap
    private val viewModel: MapViewModel by viewModels()
    private val uploadViewModel: UploadViewModel
            by navGraphViewModels(R.id.nav_nested_upload){ defaultViewModelProviderFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment = this

        initView()
    }

    private fun initView() {
        binding.tbMapToolbar.setupWithNavController(findNavController())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        subscribeUi()
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedLocationItem.collect { mapQueryItem ->
                    if (mapQueryItem != null && ::naverMap.isInitialized) {
                        makeMarkerWithCameraMove(mapQueryItem)
                    }
                }
            }
        }
    }

    private fun makeMarkerWithCameraMove(placeItem: MapQueryItem) {
        val cameraUpdate = CameraUpdate.scrollTo(
            LatLng(
                placeItem.y.toDouble(),
                placeItem.x.toDouble())
        ).animate(CameraAnimation.Easing)

        naverMap.moveCamera(cameraUpdate)

        val marker = Marker()
        marker.position = LatLng(placeItem.y.toDouble(), placeItem.x.toDouble())
        marker.captionText = placeItem.placeName
        marker.map = naverMap
    }

    fun showMapSearchPage() {
        MapSearchFragment.newInstance().show(childFragmentManager, MapSearchFragment.TAG)
    }

    fun popBackStackAfterUpdateMapQuery(item: MapQueryItem) {
        uploadViewModel.updatePlaceItem(item)
        findNavController().popBackStack()
    }
}