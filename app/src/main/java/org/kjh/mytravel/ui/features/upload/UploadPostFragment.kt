package org.kjh.mytravel.ui.features.upload

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUploadPostBinding
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.home.HomeViewModel
import org.kjh.mytravel.ui.features.profile.LineIndicatorDecoration
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleMenuItemClick

@AndroidEntryPoint
class UploadPostFragment
    : BaseFragment<FragmentUploadPostBinding>(R.layout.fragment_upload_post) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val homeViewModel     : HomeViewModel by activityViewModels()
    private val uploadViewModel: UploadViewModel
            by navGraphViewModels(R.id.nav_nested_upload) { defaultViewModelProviderFactory }
    private val uploadTempImagesAdapter by lazy {
        UploadTempImagesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uploadViewModel = uploadViewModel
        binding.myProfileViewModel = myProfileViewModel
        binding.fragment = this

        initView()
        subscribeUploadState()
    }

    private fun initView() {
        binding.tbWritePostToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_upload)
            onThrottleMenuItemClick { menu ->
                when (menu.itemId) {
                    R.id.upload -> uploadViewModel.requestUploadPost()
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

    private fun subscribeUploadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadViewModel.uploadState.collect { state ->
                    when (state) {
                        is UiState.Success -> handleSuccessCase(state.data)
                        is UiState.Error -> handleErrorCase(state.exception)
                    }
                }
            }
        }
    }

    private fun handleSuccessCase(updatedUser: User) {
        myProfileViewModel.updateMyProfile(profileItem = updatedUser)
        homeViewModel.refreshLatestPosts(true)
        navigateProfileWhenSuccessUpload()
    }

    private fun handleErrorCase(error: Throwable) {
        error.message?.let {
            showToast(it)
            uploadViewModel.initUploadState()
        }
    }

    private fun navigateProfileWhenSuccessUpload() {
        navigateTo(UploadPostFragmentDirections.actionGlobalProfileFragment())
    }

    fun navigateToLocationPage() {
        navigateTo(UploadPostFragmentDirections.actionToLocationFragment())
    }

    fun navigateToContentInputPage() {
        navigateTo(UploadPostFragmentDirections.actionToContentInput())
    }
}