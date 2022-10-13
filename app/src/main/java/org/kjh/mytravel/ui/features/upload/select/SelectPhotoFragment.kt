package org.kjh.mytravel.ui.features.upload.select

import android.net.Uri
import android.os.Build
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
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
import org.kjh.mytravel.ui.common.Dialogs
import org.kjh.mytravel.ui.features.upload.UploadViewModel
import org.kjh.mytravel.utils.hasPermission
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.onThrottleMenuItemClick
import org.kjh.mytravel.utils.startActivityToSystemSettings

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

        binding.layoutSelectPhotoToolbar.tbToolBar.apply {
            inflateMenu(R.menu.menu_next)
            onThrottleMenuItemClick { menu ->
                when (menu.itemId) {
                    R.id.next -> navigateTo(SelectPhotoFragmentDirections.actionToUploadDayLog())
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

                binding.layoutSelectPhotoToolbar.tbToolBar.menu[0].isVisible =
                    uploadItem.selectedImageItems.isNotEmpty()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        checkPermissionWithAction()
    }

    override fun onDestroyView() {
        viewModel.updateMotionAnimEnabled(false)
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        uploadViewModel.clearUploadItem()
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

    private fun checkPermissionWithAction() {
        if (hasPermission()) {
            return
        }

        Dialogs.showDefaultDialog(
            ctx   = requireContext(),
            title = getString(R.string.perm_title_retry),
            msg   = getString(R.string.perm_msg_retry),
            negAction = { findNavController().popBackStack() },
            posAction = { startActivityToSystemSettings() }
        )
    }
}