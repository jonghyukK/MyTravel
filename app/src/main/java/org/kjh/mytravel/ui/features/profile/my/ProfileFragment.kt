package org.kjh.mytravel.ui.features.profile.my

import android.Manifest
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.WindowInsets
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.BuildConfig
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.POSTS_GRID_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.POSTS_LINEAR_PAGE_INDEX
import org.kjh.mytravel.ui.features.profile.PostsTabPagerAdapter
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleMenuItemClick
import org.kjh.mytravel.utils.statusBarHeight


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
            showDialogForPermission(false) { goToSystemSettingsForPermission() }
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
        val hasPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PERMISSION_GRANTED

        when {
            hasPermission -> navigateTo(ProfileFragmentDirections.actionToSelectPhoto())

            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showDialogForPermission(false) { goToSystemSettingsForPermission() }
            }

            else -> {
                if (!hasPermission) {
                    showDialogForPermission(true) {
                        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            }
        }
    }

    private fun showDialogForPermission(
        isFirstRequest: Boolean,
        positiveAction: () -> Unit
    ) {
        val title = if (isFirstRequest)
            getString(R.string.perm_title_first)
        else
            getString(R.string.perm_title_retry)

        val msg = if (isFirstRequest)
            getString(R.string.perm_msg_first)
        else
            getString(R.string.perm_msg_retry)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.confirm)) { _, _ -> positiveAction() }
            .show()
    }

    private fun goToSystemSettingsForPermission() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data   = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            flags  = FLAG_ACTIVITY_NEW_TASK
        })
    }

    fun navigateToProfileEditPage(myProfileItem: User) {
        navigateTo(ProfileFragmentDirections.actionToProfileEdit(
                myProfileItem.profileImg, myProfileItem.nickName, myProfileItem.introduce
            )
        )
    }
}