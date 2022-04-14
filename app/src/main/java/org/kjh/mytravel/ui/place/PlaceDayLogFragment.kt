package org.kjh.mytravel.ui.place

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentPlaceDayLogBinding
import org.kjh.mytravel.ui.base.BaseFragment

class PlaceDayLogFragment
    : BaseFragment<FragmentPlaceDayLogBinding>(R.layout.fragment_place_day_log) {

    private val viewModel: PlaceViewModel by viewModels({ requireParentFragment() })

    private val placeDayLogListAdapter by lazy {
        PlaceDayLogListAdapter { item -> onClickUserInfoInPost(item.email) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initPostListByPlace()
    }

    private fun initPostListByPlace() {
        binding.rvPlaceDayLogList.apply {
            adapter = placeDayLogListAdapter
            setHasFixedSize(true)
        }
    }

    private fun onClickUserInfoInPost(email: String) {
        navigateWithAction(NavGraphDirections.actionGlobalUserFragment(email))
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