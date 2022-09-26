package org.kjh.mytravel.ui

import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.ActivityMainBinding
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.base.BaseActivity
import org.kjh.mytravel.ui.common.Dialogs
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.home.HomeViewModel
import org.kjh.mytravel.ui.features.upload.UploadViewModel
import org.kjh.mytravel.utils.NotificationUtils

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val homeViewModel  : HomeViewModel by viewModels()
    private val uploadViewModel: UploadViewModel by viewModels()
    private lateinit var navController: NavController

    private var uploadHandleJob: Job? = null

    override fun initView() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        navController = navHostFragment.navController.apply {
            addOnDestinationChangedListener { _, destination, arguments ->
                binding.bnvBottomNav.isVisible =
                    arguments?.getBoolean(getString(R.string.key_show_bnv), false) == true

                if (destination.id == R.id.homeFragment) {
                    changeStartDestination(this)
                }
            }
        }

        binding.bnvBottomNav.setupWithNavController(navController)
    }

    private fun changeStartDestination(navController: NavController) {
        val navGraph = navController.graph

        if (navGraph.startDestinationId != R.id.home) {
            navGraph.setStartDestination(R.id.home)

            navController.graph = navGraph
        }
    }

    override fun subscribeUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    uploadViewModel.uploadState.collect { uploadState ->
                        if (uploadState !is UiState.Init && uploadHandleJob == null)
                            addUploadHandleJobIfUploadRequested()
                    }
                }

                launch {
                    eventHandler.event.collect { event ->
                        when (event) {
                            is Event.ShowLoginDialogEvent -> {
                                Dialogs.showDefaultDialog(
                                    ctx = this@MainActivity,
                                    title = getString(R.string.title_need_login_dialog),
                                    msg = getString(R.string.desc_need_login_dialog),
                                    posAction = {
                                        val action = NavGraphDirections.actionGlobalNotLogin()
                                        navController.navigate(action)
                                    }
                                )
                            }

                            is Event.ApiError -> showToast(event.errorMsg)
                            else -> {}
                        }
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