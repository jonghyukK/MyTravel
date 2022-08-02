package org.kjh.mytravel.ui.features.profile.my

import android.os.Build
import android.view.WindowInsets
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.updatePadding
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
import org.kjh.mytravel.ui.common.Dialogs
import org.kjh.mytravel.ui.features.profile.POSTS_GRID_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.POSTS_LINEAR_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.PostsTabPagerAdapter
import org.kjh.mytravel.utils.*


@AndroidEntryPoint
class ProfileFragment
    : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            navigateTo(ProfileFragmentDirections.actionToSelectPhoto())
        } else {
            Dialogs.showDefaultDialog(
                ctx   = requireContext(),
                title = getString(R.string.perm_title_retry),
                msg   = getString(R.string.perm_msg_retry),
                posAction = { startActivityToSystemSettings() }
            )
        }
    }

    override fun initView() {
        binding.fragment = this
        binding.myProfileViewModel = myProfileViewModel

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.cdlProfileContainer.setOnApplyWindowInsetsListener { v, insets ->
                val sysWindow = insets.getInsets(WindowInsets.Type.statusBars())
                binding.tbProfileToolbar.updatePadding(top = sysWindow.top)
                binding.clProfileInfoContainer.updatePadding(top = sysWindow.top)
                insets
            }
        } else {
            binding.tbProfileToolbar.updatePadding(top = requireContext().statusBarHeight())
            binding.clProfileInfoContainer.updatePadding(top = requireContext().statusBarHeight())
        }

        binding.tbProfileToolbar.apply {
            inflateMenu(R.menu.menu_profile)
            onThrottleMenuItemClick { menuItem ->
                when (menuItem.itemId) {
                    R.id.write_post -> checkPermission()
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

    override fun subscribeUi() {
        myProfileViewModel.isNotLogIn
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { isNotLogin ->
                if (isNotLogin != null && isNotLogin) {
                    navigateTo(ProfileFragmentDirections.actionToNotLogin())
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun checkPermission() {
        when {
            hasPermission() -> navigateTo(ProfileFragmentDirections.actionToSelectPhoto())

            shouldShowRequestPermissionRationale(PERM_READ_EXTERNAL_STORAGE) -> {
                Dialogs.showDefaultDialog(
                    ctx   = requireContext(),
                    title = getString(R.string.perm_title_retry),
                    msg   = getString(R.string.perm_msg_retry),
                    posAction = { startActivityToSystemSettings() }
                )
            }

            else -> {
                if (!hasPermission()) {
                    Dialogs.showDefaultDialog(
                        ctx   = requireContext(),
                        title = getString(R.string.perm_title_first),
                        msg   = getString(R.string.perm_msg_first),
                        posAction = { requestPermissionLauncher.launch(PERM_READ_EXTERNAL_STORAGE) }
                    )
                }
            }
        }
    }

    fun navigateToProfileEditPage(myProfileItem: User) {
        navigateTo(ProfileFragmentDirections.actionToProfileEdit(
                myProfileItem.profileImg, myProfileItem.nickName, myProfileItem.introduce
            )
        )
    }
}