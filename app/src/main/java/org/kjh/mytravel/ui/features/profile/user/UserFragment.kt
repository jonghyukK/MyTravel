package org.kjh.mytravel.ui.features.profile.user

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
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.profile.POSTS_GRID_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.POSTS_LINEAR_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.PostsTabPagerAdapter
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment
    : BaseFragment<FragmentUserBinding>(R.layout.fragment_user){

    @Inject
    lateinit var userViewModelFactory: UserViewModel.UserNameAssistedFactory
    private val args: UserFragmentArgs by navArgs()
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val viewModel: UserViewModel by viewModels {
        UserViewModel.provideFactory(userViewModelFactory, args.userEmail)
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment  = this

        binding.tbUserToolbar.setupWithNavController(findNavController())

        val tabLayout = binding.postsTabLayout
        val viewPager = binding.postsViewPager.apply {
            adapter = PostsTabPagerAdapter(this@UserFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                POSTS_GRID_PAGE_INDEX -> tab.setIcon(R.drawable.ic_grid_view)
                POSTS_LINEAR_PAGE_INDEX -> tab.setIcon(R.drawable.ic_linear_view)
            }
        }.attach()
    }

    override fun subscribeUi() {
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
}