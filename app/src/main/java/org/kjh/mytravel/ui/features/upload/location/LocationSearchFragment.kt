package org.kjh.mytravel.ui.features.upload.location

import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsFragmentLocationSearchBinding
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment
import org.kjh.mytravel.ui.common.UiState

@AndroidEntryPoint
class LocationSearchFragment
    : BaseBottomSheetDialogFragment<BsFragmentLocationSearchBinding>(R.layout.bs_fragment_location_search) {

    private val viewModel   : LocationSearchViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels({ requireParentFragment() })
    private val locationQueryResultAdapter by lazy {
        LocationQueryResultAdapter(onClickQueryItem = ::dismissAfterSetMapQueryItem)
    }

    private fun dismissAfterSetMapQueryItem(item: MapQueryItem) {
        locationViewModel.setTempPlaceItem(item)
        dismiss()
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment  = this

        binding.rvQueryResultRecyclerView.apply {
            setHasFixedSize(true)
            adapter = locationQueryResultAdapter
        }

        binding.etMapQuery.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                viewModel.requestSearchPlaceByQuery()
            false
        }
    }

    override fun subscribeUi() {
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
        const val TAG = "LocationSearchFragment"

        @JvmStatic
        fun newInstance() = LocationSearchFragment()
    }
}