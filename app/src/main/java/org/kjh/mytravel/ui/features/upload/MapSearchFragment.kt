package org.kjh.mytravel.ui.features.upload

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsFragmentMapSearchBinding
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment
import org.kjh.mytravel.ui.common.UiState

@AndroidEntryPoint
class MapSearchFragment
    : BaseBottomSheetDialogFragment<BsFragmentMapSearchBinding>(R.layout.bs_fragment_map_search) {

    private val viewModel   : MapSearchViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels({ requireParentFragment() })
    private val mapSearchPlaceListAdapter by lazy {
        MapSearchPlaceListAdapter(onClickQueryItem = ::dismissAfterSetMapQueryItem)
    }

    private fun dismissAfterSetMapQueryItem(item: MapQueryItem) {
        mapViewModel.setTempPlaceItem(item)
        dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment  = this

        initView()
        subscribeUi()
    }

    private fun initView() {
        binding.rvSearchList.apply {
            setHasFixedSize(true)
            adapter = mapSearchPlaceListAdapter
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                viewModel.requestSearchPlaceByQuery()
            false
        }
    }

    private fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state is UiState.Error) {
                        state.exception.message?.let {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                            viewModel.initUiState()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "MapSearchFragment"

        @JvmStatic
        fun newInstance() = MapSearchFragment()
    }
}