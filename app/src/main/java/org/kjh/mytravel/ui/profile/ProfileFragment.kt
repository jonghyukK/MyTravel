package org.kjh.mytravel.ui.profile

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.domain.entity.Post
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.ProfilePostsGridItemDecoration
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.ui.MainActivity
import org.kjh.mytravel.ui.PostSmallListAdapter
import org.kjh.mytravel.ui.base.BaseFragment

@AndroidEntryPoint
class ProfileFragment
    :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by activityViewModels()

    private val myPostListAdapter by lazy {
        PostSmallListAdapter(0, { item -> onClickPostItem(item)}) { item ->
            viewModel.updateMyBookmark(item)
        }
    }

    private fun onClickPostItem(item: Post) {
        navigateWithAction(
            NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
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
        binding.fragment = this

        initToolbarWithNavigation()
        initMyPostRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.isLoggedIn) {
                        uiState.userItem?.let {
                            myPostListAdapter.submitList(it.posts)
                        }
                    } else {
                        navigateWithAction(
                            ProfileFragmentDirections.actionProfileFragmentToNotLoginFragment())
                    }
                }
            }
        }
    }

    fun onClickProfileEdit(v :View) {
        viewModel.uiState.value.userItem?.let {
            navigateWithAction(
                ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(
                    it.profileImg,
                    it.nickName,
                    it.introduce
                )
            )
        }
    }

    private fun initToolbarWithNavigation() {
        val appConfig = (requireActivity() as MainActivity).appBarConfiguration

        binding.tbProfileToolbar.apply {
            setupWithNavController(findNavController(), appConfig)
            inflateMenu(R.menu.menu_profile)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.write_post -> {
                        navigateWithAction(
                            ProfileFragmentDirections.actionProfileFragmentToSelectPhotoFragment())
                        true
                    }
                    R.id.settings -> {
                        navigateWithAction(
                            ProfileFragmentDirections.actionProfileFragmentToSettingFragment())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initMyPostRecyclerView() {
        binding.rvMyPostList.apply {
            adapter = myPostListAdapter
            addItemDecoration(ProfilePostsGridItemDecoration(requireContext()))
        }
    }

    private fun navigateWithAction(action: NavDirections) {
        findNavController().navigate(action)
    }
}