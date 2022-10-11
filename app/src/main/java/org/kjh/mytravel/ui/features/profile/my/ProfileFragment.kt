package org.kjh.mytravel.ui.features.profile.my

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.Dialogs
import org.kjh.mytravel.ui.features.profile.DAY_LOGS_GRID_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.DAY_LOGS_LINEAR_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.DayLogsTabPagerAdapter
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
            showDialogForRequestPermissionRetry()
        }
    }

    override fun initView() {
        binding.fragment = this
        binding.myProfileViewModel = myProfileViewModel

        binding.layoutProfileToolbar.tbToolBar.apply {
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
            adapter = DayLogsTabPagerAdapter(this@ProfileFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                DAY_LOGS_GRID_PAGE_INDEX -> tab.setIcon(R.drawable.ic_grid_view)
                DAY_LOGS_LINEAR_PAGE_INDEX -> tab.setIcon(R.drawable.ic_linear_view)
            }
        }.attach()
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myProfileViewModel.loginUiState.collect { loginState ->
                    when (loginState) {
                        is LoginState.Uninitialized -> postponeEnterTransition()
                        is LoginState.NotLoggedIn -> {
                            navigateTo(NavGraphDirections.actionGlobalNotLogin())
                            startPostponedEnterTransition()
                        }
                        is LoginState.LoggedIn -> startPostponedEnterTransition()
                    }
                }
            }
        }
    }

    private fun checkPermission() {
        when {
            hasPermission() -> navigateTo(ProfileFragmentDirections.actionToSelectPhoto())

            shouldShowRequestPermissionRationale(PERM_READ_EXTERNAL_STORAGE) ->
                showDialogForRequestPermissionRetry()

            else -> {
                if (!hasPermission()) {
                    showDialogForRequestPermissionFirst()
                }
            }
        }
    }

    private fun showDialogForRequestPermissionFirst() {
        Dialogs.showDefaultDialog(
            ctx   = requireContext(),
            title = getString(R.string.perm_title_first),
            msg   = getString(R.string.perm_msg_first),
            posAction = { requestPermissionLauncher.launch(PERM_READ_EXTERNAL_STORAGE) }
        )
    }

    private fun showDialogForRequestPermissionRetry() {
        Dialogs.showDefaultDialog(
            ctx   = requireContext(),
            title = getString(R.string.perm_title_retry),
            msg   = getString(R.string.perm_msg_retry),
            posAction = { startActivityToSystemSettings() }
        )
    }

    val navigateToProfileEditPage = fun() {
        navigateTo(ProfileFragmentDirections.actionToProfileEdit())
    }
}