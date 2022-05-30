package org.kjh.mytravel.ui.features.profile.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.base.BaseFragment
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
        observeLoginState()
    }

    private fun initView() {
        // init Toolbar with Menu.
        binding.tbProfileToolbar.apply {
            inflateMenu(R.menu.menu_profile)
            onThrottleMenuItemClick { menuItem ->
                when (menuItem.itemId) {
                    R.id.write_post -> navigateTo(ProfileFragmentDirections.actionToSelectPhoto())
                    R.id.settings   -> navigateTo(ProfileFragmentDirections.actionToSetting())
                }
            }
        }

        // init ViewPager with Tab Layout.
        binding.postsViewPager.apply {
            adapter = PostsTabPagerAdapter(this@ProfileFragment)
            isUserInputEnabled = false
        }
        TabLayoutMediator(binding.postsTabLayout, binding.postsViewPager) { tab, position ->
            when (position) {
                TAB_GRID     -> tab.setIcon(R.drawable.ic_grid_view)
                TAB_VERTICAL -> tab.setIcon(R.drawable.ic_linear_view)
            }
        }.attach()
    }

    private fun observeLoginState() {
        myProfileViewModel.isLoggedIn
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { isLoggedIn ->
                if (!isLoggedIn) {
                    navigateTo(ProfileFragmentDirections.actionToNotLogin())
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun navigateToProfileEditPage(myProfileItem: User) {
        navigateTo(
            ProfileFragmentDirections.actionToProfileEdit(
                myProfileItem.profileImg, myProfileItem.nickName, myProfileItem.introduce
            )
        )
    }

    companion object {
        const val TAB_GRID = 0
        const val TAB_VERTICAL = 1
    }
}