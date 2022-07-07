package org.kjh.mytravel.ui.features.place.subcity

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlacesBySubCityBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.ui.base.BaseMapFragment
import javax.inject.Inject

// todo : SubCity Page UI 경량화 작업 필요.

@AndroidEntryPoint
class PlacesBySubCityFragment :
    BaseMapFragment<FragmentPlacesBySubCityBinding>(R.layout.fragment_places_by_sub_city)
{
    @Inject
    lateinit var subCityNameAssistedFactory: PlacesBySubCityViewModel.SubCityNameAssistedFactory

    private val args: PlacesBySubCityFragmentArgs by navArgs()
    private val placeListByCityNameAdapter by lazy { PlacesBySubCityListAdapter() }
    private val viewModel: PlacesBySubCityViewModel by viewModels {
        PlacesBySubCityViewModel.provideFactory(subCityNameAssistedFactory, args.cityName)
    }

    override fun initView() {
        binding.viewModel   = viewModel
        binding.subCityName = args.cityName
        binding.fragment    = this

        binding.tbPlaceListByCityNameToolbar.setupWithNavController(findNavController())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

        binding.rvPlaceListBySubCityName.apply {
            setHasFixedSize(true)
            adapter = placeListByCityNameAdapter
        }
    }

    override fun subscribeUi() {
        viewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                if (uiState.placeBySubCityItems.isNotEmpty()) {
                    makeMarkerWithCameraMove(uiState.placeBySubCityItems)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun makeMarkerWithCameraMove(placeItems: List<Place>) {
        val rightMost = placeItems.maxOf { place -> place.x }.toDouble()
        val leftMost  = placeItems.minOf { place -> place.x }.toDouble()
        val centerOfHorizontal = (rightMost + leftMost) / 2

        val topMost    = placeItems.minOf { it.y }.toDouble()
        val bottomMost = placeItems.maxOf { it.y }.toDouble()
        val centerOfVertical = (topMost + bottomMost) / 2

        val camera = CameraUpdate.scrollAndZoomTo(
            LatLng(
                centerOfVertical,
                centerOfHorizontal,
            ), 9.0
        ).animate(CameraAnimation.None)

        if (!viewModel.uiState.value.isInitializedCameraMove) {
            naverMap.moveCamera(camera)
            viewModel.initializedCameraMove()
        }

        placeItems.forEach { place ->
            val marker = Marker().apply {
                position = LatLng(place.y.toDouble(), place.x.toDouble())
                captionText = place.placeName
            }
            marker.map = naverMap
        }
    }

    fun scrollToTop() {
        binding.mlPlaceBySubCityContainer.transitionToStart()
    }
}
