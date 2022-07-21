package org.kjh.mytravel.ui.features.upload.select

import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.selection.SelectionTracker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentSelectPhotoBinding
import org.kjh.mytravel.model.MediaStoreImage
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.upload.UploadViewModel
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleMenuItemClick

@AndroidEntryPoint
class SelectPhotoFragment
    : BaseFragment<FragmentSelectPhotoBinding>(R.layout.fragment_select_photo) {

    private lateinit var tracker: SelectionTracker<Uri>
    private val viewModel: SelectPhotoViewModel by viewModels()
    private val uploadViewModel: UploadViewModel by activityViewModels()
    private val mediaStoreImagesAdapter by lazy { MediaStoreImageListAdapter() }
    private val selectedPhotoListAdapter by lazy {
        SelectedPhotoListAdapter {
            tracker.deselect(it.contentUri)
        }
    }

    override fun initView() {
        binding.viewModel       = viewModel
        binding.uploadViewModel = uploadViewModel

        binding.tbSelectPhotoToolbar.apply {
            setupWithNavController(findNavController())
            inflateMenu(R.menu.menu_next)
            onThrottleMenuItemClick { menu ->
                when (menu.itemId) {
                    R.id.next -> navigateTo(SelectPhotoFragmentDirections.actionToUploadPost())
                }
            }
        }

        binding.mediaStoreImgRecyclerView.apply {
            adapter = mediaStoreImagesAdapter
            setHasFixedSize(true)
            addItemDecoration(MediaStoreImagesGridDecoration())
        }.also {
            tracker = MediaStoreSelectionTracker(it) {
                updateSelectedMediaStoreItems()
            }.getTracker()

            mediaStoreImagesAdapter.tracker = tracker
        }

        binding.selectedImgRecyclerView.apply {
            adapter = selectedPhotoListAdapter
            addItemDecoration(SelectedPhotoItemsDecoration())
        }
    }

    override fun subscribeUi() {
        uploadViewModel.uploadItem
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uploadItem ->
                setTrackerItemSelected(uploadItem.selectedImageItems)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setTrackerItemSelected(selectedItems: List<MediaStoreImage>) {
        if (selectedItems.isNotEmpty() && tracker.selection.size() == 0) {
            val list = selectedItems.map { it.contentUri }
            tracker.setItemsSelected(list, true)
        }
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

    override fun onDestroyView() {
        viewModel.updateMotionAnimEnabled(false)
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        uploadViewModel.clearUploadItem()
    }
}