package org.kjh.mytravel.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.model.Banner
import org.kjh.mytravel.model.PlaceWithRanking
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.base.UiState
import org.kjh.mytravel.ui.home.banner.HomeBannerItemDecoration
import org.kjh.mytravel.ui.home.banner.HomeBannersAdapter
import org.kjh.mytravel.ui.home.ranking.PlaceRankingListAdapter
import org.kjh.mytravel.ui.home.ranking.PlaceRankingListOuterAdapter


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()
    private var bannerCount = 0

    // Home "Banner" Adapter.
    private val homeBannerAdapter by lazy {
        HomeBannersAdapter { item ->
            navigateWithAction(
                HomeFragmentDirections.actionHomeFragmentToPlaceListByCityNameFragment(item.bannerTopic))
        }.apply {
            setHasStableIds(true)
        }
    }

    // Home "Ranking" Adapter.
    private val placeRankingAdapter by lazy {
        PlaceRankingListAdapter { item ->
            navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(item.place.placeName))
        }
    }

    // Home "Recent Place" Adapter.
    private val recentPlaceAdapter by lazy {
        RecentPlaceListAdapter { item ->
            navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
        }
    }

    // [Ranking, Recent Place] Concat Adapter.
    private val homeConcatAdapter by lazy {
        ConcatAdapter(PlaceRankingListOuterAdapter(placeRankingAdapter), recentPlaceAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeConcatAdapter  = homeConcatAdapter
        binding.homeBannersAdapter = homeBannerAdapter
        binding.viewModel = viewModel

        initAppBarWithCollapsingToolbarLayout()
        initHomeBannerRecyclerView()


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeBannersUiState.collect { uiState ->
                    handleHomeBannerState(uiState)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeRankingsUiState.collect { uiState ->
                    handlePlaceRankingState(uiState)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiState.collect { uiState ->
                    if (uiState.isRefresh) {
                        recentPlaceAdapter.refresh()
                        binding.rvHomeRecyclerView.scrollToPosition(0)
                        viewModel.refreshRecentPosts(false)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getRecentPostPagingData().collectLatest {
                    recentPlaceAdapter.submitData(pagingData = it)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handleHomeBannerState(uiState: UiState) {
        when (uiState) {
            is UiState.Success<*> -> {
                val items = uiState.data as List<Banner>
                bannerCount = items.size
                homeBannerAdapter.submitList(items)
            }
            is UiState.Error ->
                Toast.makeText(requireContext(), getString(R.string.error_home_banner_api), Toast.LENGTH_SHORT).show()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun handlePlaceRankingState(uiState: UiState) {
        when (uiState) {
            is UiState.Success<*> -> {
                placeRankingAdapter.submitList(uiState.data as List<PlaceWithRanking>)
            }
            is UiState.Error ->
                Toast.makeText(requireContext(), getString(R.string.error_home_ranking_api), Toast.LENGTH_SHORT).show()
        }
    }

    // init AppBar, CollapsingToolbar, Toolbar
    private fun initAppBarWithCollapsingToolbarLayout() {
        with (binding) {
            var scrollRange = -1
            binding.ablHomeAppBarLayout.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout?.totalScrollRange!!
                    }

                    viewModel?.updateCollapsingState(scrollRange + verticalOffset == 0)
                }
            )
        }
    }

    // init Home Banner RecyclerView.
    private fun initHomeBannerRecyclerView() {
        binding.rvHomeBannerList.apply {
            setHasFixedSize(true)

            addItemDecoration(HomeBannerItemDecoration())

            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)

            // circulate for "Home Banner Loop".
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                val linearLayoutManager = (this@apply.layoutManager as LinearLayoutManager)

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val firstItemVisible: Int = linearLayoutManager.findFirstVisibleItemPosition()

                    if (firstItemVisible != 1 && (firstItemVisible % bannerCount == 1)) {
                        linearLayoutManager.scrollToPosition(1)
                    } else if (firstItemVisible == 0) {
                        linearLayoutManager.scrollToPositionWithOffset(
                            bannerCount,
                            -recyclerView.computeHorizontalScrollOffset()
                        )
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.rvHomeRecyclerView.adapter == null) {
            binding.rvHomeRecyclerView.adapter = homeConcatAdapter
        }
    }

    override fun onDestroyView() {
        binding.rvHomeRecyclerView.adapter = null
        super.onDestroyView()
    }
}
