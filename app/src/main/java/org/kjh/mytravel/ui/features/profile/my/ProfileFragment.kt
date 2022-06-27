package org.kjh.mytravel.ui.features.profile.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.POSTS_GRID_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.POSTS_LINEAR_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.PostsTabPagerAdapter
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleMenuItemClick


@AndroidEntryPoint
class ProfileFragment
    :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.myProfileViewModel = myProfileViewModel

        initView()
        subscribeUi()
    }

    private fun initView() {
        binding.tbProfileToolbar.apply {
            inflateMenu(R.menu.menu_profile)
            onThrottleMenuItemClick { menuItem ->
                when (menuItem.itemId) {
                    R.id.write_post -> navigateTo(ProfileFragmentDirections.actionToSelectPhoto())
                    R.id.settings   -> navigateTo(ProfileFragmentDirections.actionToSetting())
                }
            }
        }

        val tabLayout = binding.postsTabLayout
        val viewPager = binding.postsViewPager.apply {
            adapter = PostsTabPagerAdapter(this@ProfileFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                POSTS_GRID_PAGE_INDEX -> tab.setIcon(R.drawable.ic_grid_view)
                POSTS_LINEAR_PAGE_INDEX -> tab.setIcon(R.drawable.ic_linear_view)
            }
        }.attach()
    }

    private fun subscribeUi() {
        myProfileViewModel.isNotLogIn
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { isNotLogin ->
                if (isNotLogin != null && isNotLogin) {
                    navigateTo(ProfileFragmentDirections.actionToNotLogin())
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun navigateToProfileEditPage(myProfileItem: User) {
        navigateTo(ProfileFragmentDirections.actionToProfileEdit(
                myProfileItem.profileImg, myProfileItem.nickName, myProfileItem.introduce
            )
        )
    }
}