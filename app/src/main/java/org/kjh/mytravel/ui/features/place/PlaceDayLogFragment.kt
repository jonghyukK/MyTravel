package org.kjh.mytravel.ui.features.place

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
    private val placeDayLogListAdapter by lazy {
        PlaceDayLogListAdapter(
            onClickUser = { post -> navigateUserByEmail(post.email) }
        )
    }

    private fun navigateUserByEmail(email: String) {
        navigateTo(NavGraphDirections.actionGlobalUserFragment(email))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initDayLogRecyclerView()
    }

    private fun initDayLogRecyclerView() {
        binding.rvPlaceDayLogList.apply {
            setHasFixedSize(true)
            adapter = placeDayLogListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.rvPlaceDayLogList.adapter == null) {
            binding.rvPlaceDayLogList.adapter = placeDayLogListAdapter
        }
    }

    override fun onDestroyView() {
        binding.rvPlaceDayLogList.adapter = null
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaceDayLogFragment()
    }
}