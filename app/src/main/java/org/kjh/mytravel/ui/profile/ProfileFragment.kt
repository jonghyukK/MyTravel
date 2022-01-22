package org.kjh.mytravel.ui.profile

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.GridLayoutItemDecoration
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.ui.MainActivity
import org.kjh.mytravel.ui.PostSmallListAdapter
import org.kjh.mytravel.ui.base.BaseFragment


@AndroidEntryPoint
class ProfileFragment
    :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()

    private val myPostListAdapter by lazy {
        PostSmallListAdapter { item ->
            val action =
                NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
            findNavController().navigate(action)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val navOptions =
                NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .setPopUpTo(
                        findNavController().graph.findStartDestination().id,
                        inclusive = false,
                        saveState = true
                    ).build()
            findNavController().navigate(R.id.home, null, navOptions)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoggedIn) {
                        myPostListAdapter.submitList(uiState.posts)
                    }
                    else
                        findNavController().navigate(R.id.notLoginFragment)
                }
            }
        }

        initToolbarWithNavigation()
        initMyPostRecyclerView()
    }

    private fun initToolbarWithNavigation() {
        val appConfig = (requireActivity() as MainActivity).appBarConfiguration

        binding.tbProfileToolbar.apply {
            setupWithNavController(findNavController(), appConfig)
            inflateMenu(R.menu.menu_write)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.write_post -> {
                        val action = ProfileFragmentDirections.actionProfileFragmentToSelectPhotoFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initMyPostRecyclerView() {
        binding.rvMyPostList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = myPostListAdapter
            addItemDecoration(GridLayoutItemDecoration(requireContext()))
        }
    }
}