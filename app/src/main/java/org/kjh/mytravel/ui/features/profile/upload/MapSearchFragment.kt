package org.kjh.mytravel.ui.features.profile.upload

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
        MapSearchPlaceListAdapter { item -> onClickQueryItem(item) }
    }

    private fun onClickSearch() {
        viewModel.makeSearchPlace()
    }

    private fun onClickQueryItem(item: MapQueryItem) {
        mapViewModel.setTempPlaceItem(item)
        dismiss()
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val bottomSheet = it.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        view?.post {
            val parent = view!!.parent as View
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>?
            bottomSheetBehavior!!.peekHeight = view!!.measuredHeight
            parent.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment = this

        initView()
    }

    private fun initView() {
        binding.rvSearchList.apply {
            setHasFixedSize(true)
            adapter = mapSearchPlaceListAdapter
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH)
                onClickSearch()
            false
        }
    }

    companion object {
        const val TAG = "MapSearchFragment"

        @JvmStatic
        fun newInstance() = MapSearchFragment()
    }
}