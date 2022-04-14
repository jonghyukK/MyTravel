package org.kjh.mytravel.ui.profile.upload

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsFragmentMapSearchBinding
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment


@AndroidEntryPoint
class MapSearchFragment
    : BaseBottomSheetDialogFragment<BsFragmentMapSearchBinding>(R.layout.bs_fragment_map_search) {

    private val viewModel: MapSearchViewModel by viewModels()
    private val mapViewModel: MapViewModel by viewModels({ requireParentFragment() })

    private val mapSearchPlaceListAdapter by lazy {
        MapSearchPlaceListAdapter { item ->
            mapViewModel.setTempPlaceItem(item)
            dismiss()
        }
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

        initSearchPlaceRecyclerView()
    }

    private fun initSearchPlaceRecyclerView() {
        binding.rvSearchList.apply {
            setHasFixedSize(true)
            adapter = mapSearchPlaceListAdapter
        }
    }

    fun onClickCancel(v: View) {
        dismiss()
    }

    companion object {
        const val TAG = "MapSearchFragment"
        @JvmStatic
        fun newInstance() = MapSearchFragment()
    }
}