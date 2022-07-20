package org.kjh.mytravel.ui.features.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.home.banner.BannerItemDecoration
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingDataAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingLoadStateAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingListAdapter
import org.kjh.mytravel.utils.dpToPx
import org.kjh.mytravel.utils.statusBarHeight

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()
    private val bannerListAdapter by lazy { BannerListAdapter() }
    private val placeRankingListAdapter by lazy { PlaceRankingListAdapter() }
    private val latestPostListAdapter by lazy { LatestPostPagingDataAdapter() }
    private val homeConcatAdapter by lazy {
        ConcatAdapter(
            PlaceRankingHorizontalWrapAdapter(placeRankingListAdapter),
            latestPostListAdapter.withLoadStateFooter(
                footer = LatestPostPagingLoadStateAdapter { latestPostListAdapter.retry() }
            ))
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
        binding.viewModel = viewModel
        binding.fragment  = this

        binding.homeBannerRecyclerView.apply {
            setHasFixedSize(true)
            adapter = bannerListAdapter
            addItemDecoration(BannerItemDecoration())
            PagerSnapHelper().attachToRecyclerView(this)
        }

        binding.homeConcatRecyclerView.apply {
            setHasFixedSize(true)
            adapter = homeConcatAdapter
        }
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.homeRankingsUiState.collect { uiState ->
                        if (uiState is UiState.Success) {
                            placeRankingListAdapter.submitList(uiState.data)
                        }
                    }
                }

                launch {
                    viewModel.latestPostsPagingData.collectLatest {
                        latestPostListAdapter.submitData(it)
                    }
                }

                launch {
                    viewModel.refreshLatestPostsPagingData.collect { isRefresh ->
                        if (isRefresh) {
                            latestPostListAdapter.refresh()
                            binding.homeConcatRecyclerView.scrollToPosition(0)
                            viewModel.refreshLatestPosts(false)
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveMotionProgress(binding.mlHomeContainer.progress)
    }
}
