package org.kjh.mytravel.ui.features.profile.upload

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsFragmentMapSearchBinding
import org.kjh.mytravel.model.MapQueryItem
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment

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
    }

    private fun initView() {
        binding.rvSearchList.apply {
            setHasFixedSize(true)
            adapter = mapSearchPlaceListAdapter
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                viewModel.makeSearchPlace()
            false
        }
    }

    companion object {
        const val TAG = "MapSearchFragment"

        @JvmStatic
        fun newInstance() = MapSearchFragment()
    }
}