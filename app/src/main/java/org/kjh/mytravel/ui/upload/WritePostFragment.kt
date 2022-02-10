package org.kjh.mytravel.ui.upload

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
import androidx.recyclerview.widget.SnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentWritePostBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.profile.ProfileViewModel

@AndroidEntryPoint
class WritePostFragment
    : BaseFragment<FragmentWritePostBinding>(R.layout.fragment_write_post) {

    private val uploadViewModel: UploadViewModel by navGraphViewModels(R.id.nav_nested_upload){ defaultViewModelProviderFactory }
    private val profileViewModel: ProfileViewModel by activityViewModels()

    private val writePostImagesAdapter by lazy {
        WritePostImagesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uploadViewModel = uploadViewModel

        initToolbarWithNavigation()
        initImageRecyclerView()
        initClickEvents()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadViewModel.uiState.collect {
                    if (it.selectedItems.isNotEmpty())
                        writePostImagesAdapter.submitList(it.selectedItems)

                    if (it.uploadSuccess && it.userItem != null) {
                        profileViewModel.updateProfileData(it.userItem)
                        navigateProfileWhenSuccessUpload()
                    }
                }
            }
        }
    }

    private fun navigateProfileWhenSuccessUpload() {
        val action = WritePostFragmentDirections.actionGlobalProfileFragment()
        findNavController().navigate(action)
    }

    private fun initClickEvents() {
        binding.tvAddLocation.setOnClickListener {
            val action = WritePostFragmentDirections.actionWritePostFragmentToMapFragment()
            findNavController().navigate(action)
        }
    }

    private fun initImageRecyclerView() {
        binding.rvSelectedImages.apply {
            adapter = writePostImagesAdapter
            val snapHelper: SnapHelper = PagerSnapHelper()
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