package org.kjh.mytravel.ui.profile.upload

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.MyProfileViewModel
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentWritePostBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.home.HomeViewModel

@AndroidEntryPoint
class WritePostFragment
    : BaseFragment<FragmentWritePostBinding>(R.layout.fragment_write_post) {

    private val uploadViewModel: UploadViewModel by navGraphViewModels(R.id.nav_nested_upload){ defaultViewModelProviderFactory }
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    private val writePostImagesAdapter by lazy {
        WritePostImagesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uploadViewModel = uploadViewModel
        binding.fragment = this

        initToolbarWithNavigation()
        initImageRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadViewModel.uploadState.collect { state ->
                    binding.pbLoading.isVisible = state is UploadState.Loading

                    when (state) {
                        is UploadState.Success -> {
                            myProfileViewModel.updateMyProfile(profileItem = state.userItem)
                            homeViewModel.refreshRecentPosts(true)
                            navigateProfileWhenSuccessUpload()
                        }

                        is UploadState.Error -> {
                            state.error?.let {
                                showError(it)
                                uploadViewModel.initUploadState()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateProfileWhenSuccessUpload() {
        navigateWithAction(WritePostFragmentDirections.actionGlobalProfileFragment())
    }

    fun onClickAddLocation(v: View) {
        navigateWithAction(WritePostFragmentDirections.actionWritePostFragmentToMapFragment())
    }

    private fun initImageRecyclerView() {
        binding.rvSelectedImages.apply {
            adapter = writePostImagesAdapter
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbWritePostToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_upload)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.upload -> {
                        uploadViewModel.makeUploadPost()
                        true
                    }
                    else -> false
                }
            }
        }
    }
}