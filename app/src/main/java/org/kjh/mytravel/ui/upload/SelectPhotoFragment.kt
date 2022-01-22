package org.kjh.mytravel.ui.upload

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isEmpty
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
import org.kjh.mytravel.UploadGridLayoutItemDecor
import org.kjh.mytravel.databinding.FragmentSelectPhotoBinding
import org.kjh.mytravel.ui.base.BaseFragment

@AndroidEntryPoint
class SelectPhotoFragment : BaseFragment<FragmentSelectPhotoBinding>(R.layout.fragment_select_photo) {

    private lateinit var tracker: SelectionTracker<Uri>
    private val viewModel: SelectPhotoViewModel by navGraphViewModels(R.id.nav_nested_upload){ defaultViewModelProviderFactory }

    private val selectPhotoListAdapter by lazy {
        SelectPhotoListAdapter()
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    selectPhotoListAdapter.submitList(uiState.mediaStoreImages)

                    with(uiState.selectedItems) {
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
            adapter = selectPhotoListAdapter
            addItemDecoration(UploadGridLayoutItemDecor())
        }

        tracker = MediaStoreSelectionTracker(binding.rvLocalImages) { onSelectionChanged() }
            .getTracker()

        selectPhotoListAdapter.tracker = tracker

        if (savedInstanceState != null) {
            tracker.onRestoreInstanceState(savedInstanceState)
        }
    }

    private fun onSelectionChanged() {
        val selectedList = mutableListOf<MediaStoreImage>()

        for (i in tracker.selection) {
            selectPhotoListAdapter.currentList.find { it.contentUri == i }
                ?.let {
                    selectedList.add(it)
                }
        }

        viewModel.addSelectedImages(selectedList)
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