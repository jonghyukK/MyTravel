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
import kotlinx.coroutines.launch
import org.kjh.data.Event
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.ActivityMainBinding
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
    private var uploadHandleJob: Job? = null
    private val onDestinationChangedListener: NavController.OnDestinationChangedListener
    private lateinit var actionNavigateToNotLoginPage: () -> Unit

    init {
        onDestinationChangedListener =
            NavController.OnDestinationChangedListener { nav, destination, arguments ->
                val isShowBnv = arguments?.getBoolean(getString(R.string.key_show_bnv), false) == true

                updateBottomNavViewVisibility(isShowBnv)
                changeStartDestinationIfNotHome(
                    navController = nav,
                    destinationId = destination.id
                )
            }
    }

    private fun updateBottomNavViewVisibility(isShowBnv: Boolean) {
        binding.bnvBottomNav.isVisible = isShowBnv
    }

    private fun changeStartDestinationIfNotHome(
        navController: NavController,
        destinationId: Int
    ) {
        if (destinationId != R.id.homeFragment
            || navController.graph.startDestinationId == R.id.home) {
            return
        }

        val navGraph = navController.graph
        if (navGraph.startDestinationId != R.id.home) {
            navGraph.setStartDestination(R.id.home)

            navController.graph = navGraph
        }
    }

    override fun initView() {
        setWindowDecorFitsToFalse()
        setNavController()
    }

    private fun setWindowDecorFitsToFalse() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun setNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(onDestinationChangedListener)

        setNavigateAction(navController)
        setupWithNavControllerWithBnv(navController)
    }

    private fun setNavigateAction(navController: NavController) {
        actionNavigateToNotLoginPage =
            { navController.navigate(NavGraphDirections.actionGlobalNotLogin()) }
    }

    private fun setupWithNavControllerWithBnv(navController: NavController) {
        binding.bnvBottomNav.setupWithNavController(navController)
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
                                    posAction = actionNavigateToNotLoginPage
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
        uploadHandleJob?.cancel()
        uploadHandleJob = lifecycleScope.launch {
            uploadViewModel.uploadState.collect { uploadState ->
                when (uploadState) {
                    is UiState.Loading -> makeNotificationForUploadStart()
                    is UiState.Success -> handleUploadSuccess()
                    is UiState.Error -> handleUploadFailed()
                    UiState.Init -> {}
                }
            }
        }
    }

    private fun handleUploadSuccess() {
        updateNotificationWhenUploadSuccessOrFailed(getString(R.string.upload_success))
        refreshHomeLatestDayLogs()
        initUploadStateAfterUpload()
        clearUploadHandleJob()
    }

    private fun handleUploadFailed() {
        updateNotificationWhenUploadSuccessOrFailed(getString(R.string.upload_failed))
        initUploadStateAfterUpload()
        clearUploadHandleJob()
    }

    private fun makeNotificationForUploadStart() {
        NotificationUtils.makeUploadNotification(this@MainActivity)
    }

    private fun updateNotificationWhenUploadSuccessOrFailed(resultMsg: String) {
        NotificationUtils.updateUploadNotification(this@MainActivity, resultMsg)
    }

    private fun refreshHomeLatestDayLogs() {
        homeViewModel.refreshLatestDayLogs(true)
    }

    private fun initUploadStateAfterUpload() {
        uploadViewModel.initUploadState()
    }

    private fun clearUploadHandleJob() {
        uploadHandleJob = null
    }
}