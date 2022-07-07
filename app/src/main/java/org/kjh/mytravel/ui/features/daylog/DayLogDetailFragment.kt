package org.kjh.mytravel.ui.features.daylog

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentDaylogDetailBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.navigateToPlaceInfoWithDayLog
import org.kjh.mytravel.utils.statusBarHeight
import javax.inject.Inject


@AndroidEntryPoint
class DayLogDetailFragment
    : BaseFragment<FragmentDaylogDetailBinding>(R.layout.fragment_daylog_detail) {

    @Inject
    lateinit var dayLogDetailViewModelFactory: DayLogDetailViewModel.DayLogDetailAssistedFactory

    private val args: DayLogDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<DayLogDetailViewModel> {
        DayLogDetailViewModel.provideFactory(
            dayLogDetailViewModelFactory,
            args.placeName,
            args.postId
        )
    }

    private val imageListAdapter by lazy { DayLogDetailImageListAdapter() }
    private val profileListAdapter by lazy {
        DayLogDetailUserProfileListAdapter {
            viewModel.changeCurrentPostItem(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        binding.fragment = this
        binding.viewModel = viewModel

        binding.tbPlaceDetailToolbar.setupWithNavController(findNavController())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.clPlaceDetailContainer.setOnApplyWindowInsetsListener { v, insets ->
                val sysWindow = insets.getInsets(WindowInsets.Type.statusBars())
                binding.tbPlaceDetailToolbar.updatePadding(top = sysWindow.top)
                insets
            }
        } else {
            binding.tbPlaceDetailToolbar.updatePadding(top = requireContext().statusBarHeight())
        }

        binding.placeImageRecyclerView.apply {
            setHasFixedSize(true)
            adapter = imageListAdapter
            PagerSnapHelper().attachToRecyclerView(this)
            addItemDecoration(FullLineIndicatorDecoration())
        }

        binding.userProfileRecyclerView.apply {
            itemAnimator = null
            setHasFixedSize(true)
            adapter = profileListAdapter
            addItemDecoration(DayLogDetailUserProfileItemDecoration())
        }

        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            val imgRvHeight = binding.placeImageRecyclerView.measuredHeight
            val isCollapsed = scrollY > (imgRvHeight - binding.tbPlaceDetailToolbar.height)

            binding.tbPlaceDetailToolbar.apply {
                val bgColor = if (isCollapsed) Color.WHITE else Color.TRANSPARENT
                val title = if (isCollapsed) args.placeName else ""

                setBackgroundColor(bgColor)
                this.title = title
            }
        }
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.placeDetailUiState.collect { uiState ->
                    uiState.currentPostItem?.let {
                        binding.placeImageRecyclerView.scrollToPosition(0)
                        imageListAdapter.setImageItems(it.imageUrl)
                    }

                    profileListAdapter.setUserItems(uiState.wholePostItems)
                }
            }
        }
    }

    override fun onDestroyView() {
        requireActivity().window.statusBarColor = Color.WHITE
        super.onDestroyView()
    }

    fun navigateToPlaceInfoWithDayLog() {
        navigateToPlaceInfoWithDayLog(args.placeName)
    }
}