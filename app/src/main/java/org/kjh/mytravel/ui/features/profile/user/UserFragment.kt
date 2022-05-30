package org.kjh.mytravel.ui.features.profile.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.profile.PostsTabPagerAdapter
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment
    : BaseFragment<FragmentUserBinding>(R.layout.fragment_user){

    @Inject
    lateinit var userViewModelFactory: UserViewModel.UserNameAssistedFactory

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val args: UserFragmentArgs by navArgs()
    private val viewModel: UserViewModel by viewModels {
        UserViewModel.provideFactory(userViewModelFactory, args.userEmail)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment  = this

        initView()
        observe()
    }

    private fun initView() {
        // init ToolBar Navigation.
        binding.tbUserToolbar.setupWithNavController(findNavController())

        // init ViewPager with Tab Layout.
        binding.postsViewPager.apply {
            adapter = PostsTabPagerAdapter(this@UserFragment)
            isUserInputEnabled = false
        }
        TabLayoutMediator(binding.postsTabLayout, binding.postsViewPager) { tab, position ->
            when (position) {
                TAB_GRID -> tab.setIcon(R.drawable.ic_grid_view)
                TAB_VERTICAL -> tab.setIcon(R.drawable.ic_linear_view)
            }
        }.attach()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    myProfileViewModel.myProfileState.collect { state ->
                        viewModel.updatePostsWithBookmark(state.myBookmarkItems)
                    }
                }

                launch {
                    viewModel.followState.collect { state ->
                        if (state is UiState.Success) {
                            myProfileViewModel.updateMyProfile(state.data.myProfile)
                            viewModel.initFollowState()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAB_GRID = 0
        const val TAB_VERTICAL = 1
    }
}