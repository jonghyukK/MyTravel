package org.kjh.mytravel.ui.features.upload.location

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.naver.maps.map.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentLocationBinding
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.ui.base.BaseMapFragment
import org.kjh.mytravel.utils.NaverMapUtils
import org.kjh.mytravel.ui.features.upload.UploadViewModel

@AndroidEntryPoint
class LocationFragment :
    BaseMapFragment<FragmentLocationBinding>(R.layout.fragment_location)
{
    private val viewModel      : LocationViewModel by viewModels()
    private val uploadViewModel: UploadViewModel by activityViewModels()

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment = this

        binding.tbLocationToolbar.setupWithNavController(findNavController())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    uploadViewModel.uploadItem.collect { uploadItem ->
                        uploadItem.placeItem?.let {
                            makeMarkerWithCameraMove(it)
                        }
                    }
                }

                launch {
                    viewModel.selectedLocationItem.collect { mapQueryItem ->
                        mapQueryItem?.let {
                            makeMarkerWithCameraMove(mapQueryItem)
                        }
                    }
                }
            }
        }
    }

    private fun makeMarkerWithCameraMove(placeItem: MapQueryItem) {
        NaverMapUtils.getCameraUpdateObject(
            placeItem.x.toDouble(),
            placeItem.y.toDouble()
        ).also {
            naverMap.moveCamera(it)
        }

        NaverMapUtils.makeMarker(
            placeItem.x.toDouble(),
            placeItem.y.toDouble(),
            placeItem.placeName
        ).map = naverMap
    }

    fun showMapSearchPage() {
        LocationSearchFragment.newInstance().show(childFragmentManager, LocationSearchFragment.TAG)
    }

    fun popBackStackAfterUpdateMapQuery(item: MapQueryItem) {
        uploadViewModel.updatePlaceItem(item)
        findNavController().popBackStack()
    }

    override fun naverMapClickEvent() {}
}