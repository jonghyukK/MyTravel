package org.kjh.mytravel.ui.place

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceListByCityNameBinding
import org.kjh.mytravel.databinding.VhPlaceByCityNameItemBinding
import org.kjh.mytravel.databinding.VhPlaceByCityNameRowBinding
import org.kjh.mytravel.model.Place
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.base.BaseFragment
import javax.inject.Inject


@AndroidEntryPoint
class PlaceListByCityNameFragment
    : BaseFragment<FragmentPlaceListByCityNameBinding>(R.layout.fragment_place_list_by_city_name) {

    private val args: PlaceListByCityNameFragmentArgs by navArgs()

    private val placeListByCityNameAdapter by lazy {
        PlaceListByCityNameAdapter { placeName ->
            val action = NavGraphDirections.actionGlobalPlacePagerFragment(placeName)
            findNavController().navigate(action)
        }
    }

    @Inject
    lateinit var subCityNameAssistedFactory: PlaceListByCityNameViewModel.SubCityNameAssistedFactory

    private val viewModel: PlaceListByCityNameViewModel by viewModels {
        PlaceListByCityNameViewModel.provideFactory(subCityNameAssistedFactory, args.cityName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.subCityName = args.cityName

        initToolbarWithNavigation()
        initPlaceListRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    placeListByCityNameAdapter.submitList(uiState.placeItems)
                }
            }
        }
    }

    private fun initPlaceListRecyclerView() {
        binding.rvPlaceListBySubCityName.apply {
            adapter = placeListByCityNameAdapter
            setHasFixedSize(true)
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbPlaceListByCityToolbar.setupWithNavController(findNavController())
    }
}
