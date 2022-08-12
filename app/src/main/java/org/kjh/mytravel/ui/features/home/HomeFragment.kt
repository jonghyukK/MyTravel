package org.kjh.mytravel.ui.features.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.model.Banner
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.home.banner.BannerItemDecoration
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.home.banner.BannerOuterAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingDataAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingLoadStateAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingListAdapter

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val bannerListAdapter by lazy {
        BannerListAdapter()
    }
    private val placeRankingListAdapter by lazy {
        PlaceRankingListAdapter()
    }

    private val latestPostListAdapter by lazy {
        LatestPostPagingDataAdapter().apply {
            withLoadStateFooter(
                footer = LatestPostPagingLoadStateAdapter { this.retry() }
            )
        }
    }

    private val homeConcatAdapter by lazy {
        ConcatAdapter(
            BannerOuterAdapter(bannerListAdapter),
            PlaceRankingHorizontalWrapAdapter(placeRankingListAdapter),
            latestPostListAdapter
        )
    }

    override fun onCreateView(
        inflater          : LayoutInflater,
        container         : ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment  = this

        binding.homeConcatRecyclerView.apply {
            setHasFixedSize(true)
            adapter = homeConcatAdapter
        }
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.homeUiState.collect { uiState ->
                        placeRankingListAdapter.submitList(uiState.rankingItems)
                        bannerListAdapter.submitList(uiState.bannerItems)
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
}
