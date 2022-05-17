package org.kjh.mytravel.ui.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.appbar.AppBarLayout
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.model.PlaceWithRanking
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.home.banner.BannerItemDecoration
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingDataAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingLoadStateAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingListAdapter
import org.kjh.mytravel.utils.navigatePlaceDetailByPlaceName
import org.kjh.mytravel.utils.navigateWithAction

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val bannerListAdapter by lazy {
        BannerListAdapter(onClickBanner = ::navigatePlacePageBySubCity)
    }

    private val placeRankingListAdapter by lazy {
        PlaceRankingListAdapter(onClickRanking = ::navigatePlaceDetailByPlaceName)
    }

    private val latestPostListAdapter by lazy {
        LatestPostPagingDataAdapter(onClickPost = ::navigatePlaceDetailByPlaceName)
    }

    private val placeRankingHorizontalWrapAdapter by lazy {
        PlaceRankingHorizontalWrapAdapter(placeRankingListAdapter)
    }

    private val homeConcatAdapter by lazy {
        ConcatAdapter(
            placeRankingHorizontalWrapAdapter,
            latestPostListAdapter.withLoadStateFooter(
                footer = LatestPostPagingLoadStateAdapter { latestPostListAdapter.retry() }
            ))
    }

    private fun navigatePlacePageBySubCity(subCityName: String) {
        navigateWithAction(
            HomeFragmentDirections.actionHomeFragmentToPlaceListByCityNameFragment(subCityName))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment  = this

        initHomeBannerRecyclerView()
        initHomeConcatRecyclerView()

        observePlaceRankingState()
        observeLatestPostsState()
        observeRefreshForLatestPosts()
    }

    private fun initHomeBannerRecyclerView() {
        binding.homeBannerRecyclerView.apply {
            setHasFixedSize(true)
            adapter = bannerListAdapter
            addItemDecoration(BannerItemDecoration())
            PagerSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun initHomeConcatRecyclerView() {
        binding.homeConcatRecyclerView.apply {
            setHasFixedSize(true)
            adapter = homeConcatAdapter
        }
    }

    private fun observePlaceRankingState() {
        viewModel.homeRankingsUiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(::handlePlaceRankingState)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handlePlaceRankingState(uiState: UiState<List<PlaceWithRanking>>) {
        if (uiState is UiState.Success) {
            placeRankingListAdapter.submitList(uiState.data)
        }
    }

    private fun observeLatestPostsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.latestPostsPagingData.collectLatest {
                    latestPostListAdapter.submitData(it)
                }
            }
        }
    }

    private fun observeRefreshForLatestPosts() {
        viewModel.refreshLatestPostsPagingData
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach(::handleRefreshLatestPosts)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleRefreshLatestPosts(isRefresh: Boolean) {
        if (isRefresh) {
            latestPostListAdapter.refresh()
            binding.homeConcatRecyclerView.scrollToPosition(0)
            viewModel.refreshLatestPosts(false)
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.homeConcatRecyclerView.adapter == null) {
            binding.homeConcatRecyclerView.adapter = homeConcatAdapter
        }
    }

    override fun onDestroyView() {
        binding.homeConcatRecyclerView.adapter = null
        super.onDestroyView()
    }
}
