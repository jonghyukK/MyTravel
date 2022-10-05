package org.kjh.mytravel.ui.features.place.infowithdaylog

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceDaylogListBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.DayLogGridItemDecoration

@AndroidEntryPoint
class PlaceDayLogListFragment
    : BaseFragment<FragmentPlaceDaylogListBinding>(R.layout.fragment_place_daylog_list) {

    private val viewModel: PlaceInfoWithDayLogViewModel by viewModels({ requireParentFragment() })
    private val placeDayLogListAdapter by lazy { PlaceDayLogListAdapter() }

    override fun initView() {
        binding.viewModel = viewModel

        binding.placeDayLogRecyclerView.apply {
            setHasFixedSize(true)
            adapter = placeDayLogListAdapter
            addItemDecoration(DayLogGridItemDecoration())
        }
    }

    override fun subscribeUi() {}

    companion object {
        @JvmStatic
        fun newInstance() = PlaceDayLogListFragment()
    }
}