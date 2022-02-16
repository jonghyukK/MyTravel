package org.kjh.mytravel.ui.profile.upload

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.selection.SelectionTracker
import com.example.domain.entity.MediaStoreImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.UploadGridLayoutItemDecor
import org.kjh.mytravel.databinding.FragmentSelectPhotoBinding
import org.kjh.mytravel.ui.base.BaseFragment

@AndroidEntryPoint
class SelectPhotoFragment
    : BaseFragment<FragmentSelectPhotoBinding>(R.layout.fragment_select_photo) {

    private lateinit var tracker: SelectionTracker<Uri>

    private val uploadViewModel: UploadViewModel by navGraphViewModels(R.id.nav_nested_upload){ defaultViewModelProviderFactory }
    private val viewModel: SelectPhotoViewModel by viewModels()

    private val mediaStoreImagesAdapter by lazy {
        MediaStoreImageListAdapter()
    }

    private val selectedPhotoListAdapter by lazy {
        SelectedPhotoListAdapter {
            tracker.deselect(it.contentUri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithNavigation()
        initLocalPhotoRecyclerView(savedInstanceState)
        initSelectedImagesRecyclerView()

        viewModel.mediaStoreImages.observe(viewLifecycleOwner) { mediaStoreImages ->
            mediaStoreImagesAdapter.submitList(mediaStoreImages)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadViewModel.uiState.collect { dataState ->
                    with (dataState.selectedItems) {
                        selectedPhotoListAdapter.submitList(this)
                        restoreSelectionTracker(this)
                        nextMenuIsVisible(this)
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (::tracker.isInitialized)
            tracker.onSaveInstanceState(outState)
    }

    private fun initToolbarWithNavigation() {
        binding.tbSelectPhotoToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_next)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.next -> {
                        val action = SelectPhotoFragmentDirections.actionSelectPhotoFragmentToWritePostFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initSelectedImagesRecyclerView() {
        binding.rvSelectedImages.apply {
            adapter = selectedPhotoListAdapter
        }
    }

    private fun initLocalPhotoRecyclerView(savedInstanceState: Bundle?) {
        binding.rvLocalImages.apply {
            adapter = mediaStoreImagesAdapter
            addItemDecoration(UploadGridLayoutItemDecor())
        }

        tracker = MediaStoreSelectionTracker(binding.rvLocalImages) { onSelectionChanged() }
            .getTracker()

        mediaStoreImagesAdapter.tracker = tracker

        if (savedInstanceState != null) {
            tracker.onRestoreInstanceState(savedInstanceState)
        }
    }

    private fun onSelectionChanged() {
        val selectedList = mutableListOf<MediaStoreImage>()

        for (i in tracker.selection) {
            mediaStoreImagesAdapter.currentList.find { it.contentUri == i }
                ?.let {
                    selectedList.add(it)
                }
        }

        uploadViewModel.updateSelectedImages(selectedList)
    }

    private fun restoreSelectionTracker(selectedItems: List<MediaStoreImage>) {
        if (selectedItems.isNotEmpty() && tracker.selection.size() == 0) {
            val list = selectedItems.map {
                it.contentUri
            }
            tracker.setItemsSelected(list, true)
        }
    }

    private fun nextMenuIsVisible(selectedItems: List<MediaStoreImage>){
        if (selectedItems.isNotEmpty() && binding.tbSelectPhotoToolbar.menu.isEmpty()) {
            binding.tbSelectPhotoToolbar.inflateMenu(R.menu.menu_next)
        } else if (selectedItems.isEmpty())
            binding.tbSelectPhotoToolbar.menu.clear()
    }
}