package org.kjh.mytravel.ui.features.place.detail

import androidx.fragment.app.viewModels
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceDayLogBinding
import org.kjh.mytravel.ui.base.BaseFragment

class PlaceDayLogFragment
    : BaseFragment<FragmentPlaceDayLogBinding>(R.layout.fragment_place_day_log) {

    private val viewModel: PlaceViewModel by viewModels({ requireParentFragment() })
    private val placeDayLogListAdapter by lazy { PlaceDayLogListAdapter() }

    override fun initView() {
        binding.viewModel = viewModel

        binding.rvPlaceDayLogList.apply {
            setHasFixedSize(true)
            adapter = placeDayLogListAdapter
        }
    }

    override fun subscribeUi() {}

    companion object {
        @JvmStatic
        fun newInstance() = PlaceDayLogFragment()
    }
}