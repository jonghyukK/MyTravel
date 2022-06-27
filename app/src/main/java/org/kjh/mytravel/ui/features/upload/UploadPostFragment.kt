package org.kjh.mytravel.ui.features.upload

import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUploadPostBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.LineIndicatorDecoration
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleMenuItemClick

@AndroidEntryPoint
class UploadPostFragment
    : BaseFragment<FragmentUploadPostBinding>(R.layout.fragment_upload_post) {

    private val uploadViewModel   : UploadViewModel by activityViewModels()
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val uploadTempImagesAdapter by lazy {
        UploadTempImagesAdapter()
    }

    override fun initView() {
        binding.uploadViewModel = uploadViewModel
        binding.myProfileViewModel = myProfileViewModel
        binding.fragment = this

        binding.tbWritePostToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_upload)
            onThrottleMenuItemClick { menu ->
                if (menu.itemId == R.id.upload) {
                    requestUploadIfDataReady()
                }
            }
        }

        binding.rvSelectedImages.apply {
            setHasFixedSize(true)
            adapter = uploadTempImagesAdapter
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
            addItemDecoration(LineIndicatorDecoration())
        }
    }

    private fun requestUploadIfDataReady() {
        val isNotReadyData = !uploadViewModel.isReadyUploadData()

        if (isNotReadyData) {
            showToast(getString(R.string.error_check_inputs))
            return
        }

        requestUploadAndNavigateToHome()
    }

    private fun requestUploadAndNavigateToHome() {
        uploadViewModel.requestUploadPost()
        navigateToHome()
    }

    private fun navigateToHome() {
        val navController = findNavController()
        val startDestination = navController.graph.startDestinationId
        val navOptions = NavOptions.Builder()
            .setPopUpTo(startDestination, true)
            .build()

        navController.navigate(startDestination, null, navOptions)
    }

    fun navigateToLocationPage() {
        navigateTo(UploadPostFragmentDirections.actionToLocation())
    }

    fun navigateToContentInputPage() {
        navigateTo(UploadPostFragmentDirections.actionToContentInput())
    }

    override fun subscribeUi() {}
}