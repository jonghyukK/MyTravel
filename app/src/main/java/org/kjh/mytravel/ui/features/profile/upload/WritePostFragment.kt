package org.kjh.mytravel.ui.features.profile.upload

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentWritePostBinding
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.features.profile.MyProfileViewModel
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.home.HomeViewModel

interface WritePostClickEvent {
    fun onClickAddLocation(v: View)
    fun onClickUpload()
}

@AndroidEntryPoint
class WritePostFragment
    : BaseFragment<FragmentWritePostBinding>(R.layout.fragment_write_post), WritePostClickEvent {

    private val uploadViewModel   : UploadViewModel by navGraphViewModels(R.id.nav_nested_upload) { defaultViewModelProviderFactory }
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val homeViewModel     : HomeViewModel by activityViewModels()

    private val writePostImagesAdapter by lazy {
        WritePostImagesAdapter()
    }

    override fun onClickAddLocation(v: View) {
        navigateWithAction(WritePostFragmentDirections.actionWritePostFragmentToMapFragment())
    }

    override fun onClickUpload() {
        uploadViewModel.makeUploadPost()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uploadViewModel = uploadViewModel
        binding.fragment = this

        initView()
        observe()
    }

    private fun initView() {
        binding.tbWritePostToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_upload)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.upload -> {
                        onClickUpload()
                        true
                    }
                    else -> false
                }
            }
        }

        binding.rvSelectedImages.apply {
            setHasFixedSize(true)
            adapter = writePostImagesAdapter
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadViewModel.uploadState.collect { state ->
                    when (state) {
                        is UiState.Success -> handleSuccessCase(state.data)
                        is UiState.Error   -> handleErrorCase(state.errorMsg.res)
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

    private fun handleErrorCase(errorMsg: Int) {
        Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show()
        uploadViewModel.initUploadState()
    }

    private fun navigateProfileWhenSuccessUpload() {
        navigateWithAction(WritePostFragmentDirections.actionGlobalProfileFragment())
    }
}