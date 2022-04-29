package org.kjh.mytravel.ui.place.subcity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlacesBySubCityBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.utils.statusBarHeight
import javax.inject.Inject

// todo : SubCity Page UI 경량화 작업 필요.

interface PlaceByCityNameClickEvent {
    fun onClickPlaceItem(placeName: String)
    fun onClickShowMap(v: View)
}

@AndroidEntryPoint
class PlacesBySubCityFragment
    : BaseFragment<FragmentPlacesBySubCityBinding>(R.layout.fragment_places_by_sub_city),
    OnMapReadyCallback, PlaceByCityNameClickEvent
{
    @Inject
    lateinit var subCityNameAssistedFactory: PlacesBySubCityViewModel.SubCityNameAssistedFactory

    private val viewModel: PlacesBySubCityViewModel by viewModels {
        PlacesBySubCityViewModel.provideFactory(subCityNameAssistedFactory, args.cityName)
    }

    private val args: PlacesBySubCityFragmentArgs by navArgs()
    private var naverMap: NaverMap? = null
    private lateinit var bsBehavior: BottomSheetBehavior<View>

    private val placeListByCityNameAdapter by lazy {
        PlacesBySubCityListAdapter { placeName ->
            onClickPlaceItem(placeName)
        }
    }

    override fun onClickPlaceItem(placeName: String) {
        navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(placeName))
    }

    override fun onClickShowMap(v: View) {
        bsBehavior.state = STATE_COLLAPSED
        binding.bsPlaceList.rvPlaceListBySubCityName.layoutManager?.scrollToPosition(0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel   = viewModel
        binding.subCityName = args.cityName
        binding.fragment    = this

        initView()
        observe()
    }

    private fun initView() {
        initToolbarWithNavigation()
        initNaverMapFragment()
        initPlaceListBottomSheet()
    }

    private fun initToolbarWithNavigation() {
        binding.tbPlaceListByCityNameToolbar.setupWithNavController(findNavController())
    }

    private fun initNaverMapFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initPlaceListBottomSheet() {
        if (::bsBehavior.isInitialized) {
            binding.btnShowMap.isVisible = bsBehavior.state == STATE_EXPANDED
        }

        bsBehavior = from(binding.bsPlaceList.root).apply {
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        STATE_EXPANDED  -> binding.btnShowMap.isVisible = true
                        STATE_COLLAPSED -> binding.btnShowMap.isVisible = false
                    }
                }
            })

            expandedOffset = getBehaviorExpandedOffsetWithPeekHeight().first
            peekHeight     = getBehaviorExpandedOffsetWithPeekHeight().second
        }

        binding.bsPlaceList.rvPlaceListBySubCityName.adapter = placeListByCityNameAdapter
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            placeListByCityNameAdapter.submitList(uiState.data)
                            setMarkerAtPlaceList(uiState.data)
                        }
                        is UiState.Error ->
                            Toast.makeText(requireContext(), uiState.errorMsg.res, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setMarkerAtPlaceList(placeItems: List<Place>) {
        val right = placeItems.maxOf { place -> place.x }.toDouble()
        val left  = placeItems.minOf { place -> place.x }.toDouble()
        val rl = (right + left) / 2

        val top    = placeItems.minOf { it.y }.toDouble()
        val bottom = placeItems.maxOf { it.y }.toDouble()
        val tb = (top + bottom) / 2

        val camera = CameraUpdate.scrollAndZoomTo(
            LatLng(
                tb,
                rl,
            ), 9.0
        ).animate(CameraAnimation.None)

        naverMap?.moveCamera(camera)

        placeItems.forEach { place ->
            val marker = Marker()
            marker.position = LatLng(place.y.toDouble(), place.x.toDouble())
            marker.captionText = place.placeName
            marker.map = naverMap
        }
    }

    private fun getBehaviorExpandedOffsetWithPeekHeight(): Pair<Int, Int> {
        val dp = resources.displayMetrics.density
        val mapHeight    = binding.map.layoutParams.height / dp
        val tbHeight     = binding.tbPlaceListByCityNameToolbar.layoutParams.height / dp
        val deviceHeight = resources.displayMetrics.heightPixels / dp
        val statusBarHeight = requireContext().statusBarHeight() / dp

        val expandedOffset = ((tbHeight + statusBarHeight) * dp).toInt()
        val peekHeight     = ((deviceHeight - mapHeight - tbHeight) * dp).toInt()

        return Pair(expandedOffset, peekHeight)
    }

    override fun onMapReady(p0: NaverMap) {
        if (naverMap == null) {
            naverMap = p0
        }
    }

    override fun onDestroyView() {
        naverMap = null
        super.onDestroyView()
    }

}
