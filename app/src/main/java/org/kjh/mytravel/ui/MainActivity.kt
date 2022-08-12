package org.kjh.mytravel.ui

import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.ActivityMainBinding
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.base.BaseActivity
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.home.HomeViewModel
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import org.kjh.mytravel.ui.features.upload.UploadViewModel
import org.kjh.mytravel.utils.NotificationUtils

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeViewModel      : HomeViewModel by viewModels()
    private val uploadViewModel    : UploadViewModel by viewModels()
    private val myProfileViewModel : MyProfileViewModel by viewModels()

    private var uploadHandleJob: Job? = null

    override fun initView() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        val navController = navHostFragment.navController.apply {
            addOnDestinationChangedListener { _, _, arguments ->
                binding.bnvBottomNav.isVisible =
                    arguments?.getBoolean(getString(R.string.key_show_bnv), false) == true
            }
        }

        binding.bnvBottomNav.setupWithNavController(navController)
    }

    override fun subscribeUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    homeViewModel.homeUiState.collect { homeUiState ->
                        binding.bnvBottomNav.isVisible = !homeUiState.isLoading
                    }
                }

                launch {
                    uploadViewModel.uploadState.collect { uploadState ->
                        if (uploadState !is UiState.Init && uploadHandleJob == null)
                            addUploadHandleJobIfUploadRequested()
                    }
                }
            }
        }
    }

    private fun addUploadHandleJobIfUploadRequested() {
        uploadHandleJob = MainScope().launch {
            uploadViewModel.uploadState.collect { uploadState ->
                when (uploadState) {
                    is UiState.Loading -> makeNotificationForUploadStart()
                    is UiState.Success -> handleUploadSuccess(uploadState.data)
                    is UiState.Error -> handleUploadFailed()
                    UiState.Init -> {}
                }
            }
        }
    }

    private fun handleUploadSuccess(newProfileItem: User) {
        updateNotificationWhenUploadSuccessOrFailed(getString(R.string.upload_success))
        updateMyProfileItem(newProfileItem)
        refreshHomeLatestPosts()
        initUploadStateAfterUpload()
        cancelAndClearUploadJob()
    }

    private fun handleUploadFailed() {
        updateNotificationWhenUploadSuccessOrFailed(getString(R.string.upload_failed))
        initUploadStateAfterUpload()
        cancelAndClearUploadJob()
    }

    private fun makeNotificationForUploadStart() {
        NotificationUtils.makeUploadNotification(this@MainActivity)
    }

    private fun updateNotificationWhenUploadSuccessOrFailed(resultMsg: String) {
        NotificationUtils.updateUploadNotification(this@MainActivity, resultMsg)
    }

    private fun updateMyProfileItem(newProfileItem: User) {
        myProfileViewModel.updateMyProfile(newProfileItem)
    }

    private fun refreshHomeLatestPosts() {
        homeViewModel.refreshLatestPosts(true)
    }

    private fun initUploadStateAfterUpload() {
        uploadViewModel.initUploadState()
    }

    private fun cancelAndClearUploadJob() {
        uploadHandleJob?.cancel()
        uploadHandleJob = null
    }
}