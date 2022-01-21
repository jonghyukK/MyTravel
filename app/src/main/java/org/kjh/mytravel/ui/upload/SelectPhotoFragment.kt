package org.kjh.mytravel.ui.upload

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
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

@AndroidEntryPoint
class SelectPhotoFragment : Fragment() {

    private lateinit var binding: FragmentSelectPhotoBinding
    private lateinit var tracker: SelectionTracker<Uri>
    private val viewModel: SelectPhotoViewModel by navGraphViewModels(R.id.nav_nested_upload) { defaultViewModelProviderFactory }

    private val selectPhotoListAdapter by lazy {
        SelectPhotoListAdapter()
    }

    private val selectedPhotoListAdapter by lazy {
        SelectedPhotoListAdapter {
            tracker.deselect(it.contentUri)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectPhotoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
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
                    selectPhotoListAdapter.submitList(uiState.mediaItemsInBucket)
                    selectedPhotoListAdapter.submitList(uiState.selectedItems)

                    if (uiState.selectedItems.isNotEmpty() && tracker.selection.size() == 0) {
                        val list = uiState.selectedItems.map {
                            it.contentUri
                        }

                        tracker.setItemsSelected(list, true)
                    }

                    if (uiState.selectedItems.isNotEmpty() && binding.tbSelectPhotoToolbar.menu.isEmpty()) {
                        binding.tbSelectPhotoToolbar.inflateMenu(R.menu.menu_next)
                    } else if (uiState.selectedItems.isEmpty())
                        binding.tbSelectPhotoToolbar.menu.clear()
                }
            }
        }
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
        viewModel.add(selectedList)
    }
}