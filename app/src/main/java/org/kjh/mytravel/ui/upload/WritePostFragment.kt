package org.kjh.mytravel.ui.upload

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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

@AndroidEntryPoint
class WritePostFragment
    : BaseFragment<FragmentWritePostBinding>(R.layout.fragment_write_post) {

    private val parentViewModel : SelectPhotoViewModel by navGraphViewModels(R.id.nav_nested_upload) { defaultViewModelProviderFactory }

    private val writePostImagesAdapter by lazy {
        WritePostImagesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = parentViewModel

        initToolbarWithNavigation()
        initImageRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                parentViewModel.uiState.collect {
                    binding.pbLoading.isVisible = it.isUploadLoading
                    writePostImagesAdapter.submitList(it.selectedItems)

                    if (it.isUploadSuccess) {
                        val action = WritePostFragmentDirections.actionGlobalProfileFragment()
                        findNavController().navigate(action)
                    }
                }
            }
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
                        parentViewModel.uploadPost()
                        true
                    }
                    else -> false
                }
            }
        }
    }
}