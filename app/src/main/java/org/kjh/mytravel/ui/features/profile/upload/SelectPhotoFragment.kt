package org.kjh.mytravel.ui.features.profile.upload

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.selection.SelectionTracker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentSelectPhotoBinding
import org.kjh.mytravel.model.MediaStoreImage
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.utils.navigateWithAction
import org.kjh.mytravel.utils.onThrottleMenuItemClick

@AndroidEntryPoint
class SelectPhotoFragment
    : BaseFragment<FragmentSelectPhotoBinding>(R.layout.fragment_select_photo) {

    private lateinit var tracker: SelectionTracker<Uri>
    private val viewModel: SelectPhotoViewModel by viewModels()

    private val uploadViewModel: UploadViewModel by navGraphViewModels(R.id.nav_nested_upload) {
        defaultViewModelProviderFactory
    }

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
        binding.viewModel       = viewModel
        binding.uploadViewModel = uploadViewModel

        initView()
        initTracker(savedInstanceState)
        observe()
    }

    private fun initView() {
        // Toolbar.
        binding.tbSelectPhotoToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_next)
            onThrottleMenuItemClick { menu ->
                when (menu.itemId) {
                    R.id.next -> navigateToWritePostPage()
                }
            }
        }

        // RecyclerView for MediaStoreImages.
        binding.rvLocalImages.apply {
            adapter = mediaStoreImagesAdapter
            setHasFixedSize(true)
            addItemDecoration(SelectPhotoGridLayoutItemDecor())
        }

        // RecyclerView for Selected MediaStoreItems.
        binding.rvSelectedImages.apply {
            adapter = selectedPhotoListAdapter
            setHasFixedSize(true)
        }
    }

    private fun initTracker(savedInstanceState: Bundle?) {
        tracker = MediaStoreSelectionTracker(binding.rvLocalImages) { updateSelectedMediaStoreItems() }
            .getTracker()

        mediaStoreImagesAdapter.tracker = tracker

        if (savedInstanceState != null) {
            tracker.onRestoreInstanceState(savedInstanceState)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                uploadViewModel.uploadItemState.collect { uploadItemState ->
                    restoreSelectionTracker(uploadItemState.selectedItems)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::tracker.isInitialized)
            tracker.onSaveInstanceState(outState)
    }

    private fun restoreSelectionTracker(selectedItems: List<MediaStoreImage>) {
        if (selectedItems.isNotEmpty() && tracker.selection.size() == 0) {
            val list = selectedItems.map { it.contentUri }
            tracker.setItemsSelected(list, true)
        }
    }

    private fun navigateToWritePostPage() {
        navigateWithAction(
            SelectPhotoFragmentDirections.actionSelectPhotoFragmentToWritePostFragment())
    }

    private fun updateSelectedMediaStoreItems() {
        val selectedList = mutableListOf<MediaStoreImage>()

        for (uri in tracker.selection) {
            mediaStoreImagesAdapter.currentList.find { it.contentUri == uri }?.let {
                selectedList.add(it)
            }
        }

        uploadViewModel.updateSelectedImages(selectedList)
    }
}