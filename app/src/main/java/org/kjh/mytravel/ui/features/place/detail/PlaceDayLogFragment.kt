package org.kjh.mytravel.ui.features.place.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceDayLogBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.utils.navigateTo

class PlaceDayLogFragment
    : BaseFragment<FragmentPlaceDayLogBinding>(R.layout.fragment_place_day_log) {

    private val viewModel: PlaceViewModel by viewModels({ requireParentFragment() })
    private val placeDayLogListAdapter by lazy { PlaceDayLogListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        binding.rvPlaceDayLogList.apply {
            setHasFixedSize(true)
            adapter = placeDayLogListAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaceDayLogFragment()
    }
}