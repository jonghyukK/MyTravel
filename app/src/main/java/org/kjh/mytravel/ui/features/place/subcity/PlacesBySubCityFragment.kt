package org.kjh.mytravel.ui.features.place.subcity

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlacesBySubCityBinding
import org.kjh.mytravel.model.PlaceWithMarker
import org.kjh.mytravel.ui.base.BaseMapFragment
import org.kjh.mytravel.utils.navigationHeight
import org.kjh.mytravel.utils.statusBarHeight
import javax.inject.Inject

// todo : SubCity Page UI 경량화 작업 필요.

@AndroidEntryPoint
class PlacesBySubCityFragment :
    BaseMapFragment<FragmentPlacesBySubCityBinding>(R.layout.fragment_places_by_sub_city) {

    @Inject
    lateinit var subCityNameAssistedFactory: PlacesBySubCityViewModel.SubCityNameAssistedFactory

    private val args: PlacesBySubCityFragmentArgs by navArgs()
    private val placeListByCityNameAdapter by lazy { PlacesBySubCityListAdapter() }
    private val selectedPlaceImageListAdapter by lazy { PlacesBySubCityPostListAdapter() }
    private val viewModel: PlacesBySubCityViewModel by viewModels {
        PlacesBySubCityViewModel.provideFactory(subCityNameAssistedFactory, args.cityName)
    }

    private lateinit var bsBehavior: BottomSheetBehavior<ConstraintLayout>
    private var initMapHeight = 0
    private var bsPeekHeight = 0

    override fun initView() {
        binding.viewModel   = viewModel
        binding.subCityName = args.cityName
        binding.fragment    = this

        binding.tbPlaceListByCityNameToolbar.setupWithNavController(findNavController())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

        binding.placesRecyclerView.apply {
            adapter = placeListByCityNameAdapter
        }

        binding.layoutSelectedPlace.postImgRecyclerView.apply {
            setHasFixedSize(true)
            adapter = selectedPlaceImageListAdapter
            addItemDecoration(PlacesBySubCityPostItemDecoration())
        }

        initBottomSheetBehavior()
    }

    private fun initBottomSheetBehavior() {
        if (viewModel.bottomSheetState.value == STATE_HIDDEN) {
            postponeEnterTransition()
        }

        binding.map.doOnLayout {
            bsBehavior = BottomSheetBehavior.from(binding.bsContainer).apply {
                val toolBarHeight   = binding.tbPlaceListByCityNameToolbar.layoutParams.height
                val deviceHeight    = requireActivity().window.decorView.height
                val statusBarHeight = requireContext().statusBarHeight()
                val navHeight       = requireContext().navigationHeight()
                val maxExpandedHeight = deviceHeight - (statusBarHeight + toolBarHeight + navHeight)

                initMapHeight = it.measuredHeight
                bsPeekHeight  = deviceHeight - (toolBarHeight + initMapHeight + statusBarHeight + navHeight)

                maxHeight  = maxExpandedHeight
                peekHeight = bsPeekHeight
            }
        }
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.cameraMoveEvent.collect { cameraEvent ->
                        cameraEvent?.let {
                            initCameraMoveEventAfterMoveDone(it)
                        }
                    }
                }
                launch {
                    viewModel.uiState.collect { uiState ->
                        drawMarkerOnMap(uiState.placeWithMarkerMap)
                    }
                }

                launch {
                    viewModel.selectedPlaceMap.collect {
                        updateBsStateWhetherMarkerClickedOrNot(it != null)
                        adjustMapHeight(it)
                        startPostponedEnterTransition()
                    }
                }
            }
        }
    }

    override fun naverMapClickEvent() {
        viewModel.updateSelectedPlaceMap(null)
    }

    private fun initCameraMoveEventAfterMoveDone(camera: CameraUpdate) {
        naverMap.moveCamera(camera)
        viewModel.removeCameraEvent()
    }

    private fun drawMarkerOnMap(markerMap: Map<String, PlaceWithMarker>) {
        markerMap.entries.map {
            it.value.placeMarker.map = naverMap
        }
    }

    private fun updateBsStateWhetherMarkerClickedOrNot(markerClicked: Boolean) {
        if (markerClicked) {
            viewModel.updateBottomSheetState(STATE_HIDDEN)
        } else if (!markerClicked && viewModel.bottomSheetState.value == STATE_HIDDEN) {
            viewModel.updateBottomSheetState(STATE_COLLAPSED)
        }
    }

    private fun adjustMapHeight(selectedPlaceMarkerItem: PlaceWithMarker?) {
        binding.layoutSelectedPlace.clPlaceContainer.doOnLayout { selectedPlaceView ->
            val height = selectedPlaceMarkerItem?.let {
                initMapHeight + (bsPeekHeight - selectedPlaceView.height)
            } ?: initMapHeight

            binding.map.updateLayoutParams {
                this.height = height
            }
        }
    }

    fun scrollToTop() {
        viewModel.updateBottomSheetState(STATE_COLLAPSED)
        binding.placesRecyclerView.scrollToPosition(0)
    }
}
