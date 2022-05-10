package org.kjh.mytravel.ui.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.home.banner.BannerItemDecoration
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingDataAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingListAdapter

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val bannerListAdapter by lazy {
        BannerListAdapter(
            onClickBanner = { banner -> navigateToPlacesBySubCityPage(banner.bannerTopic) })
    }

    private val placeRankingListAdapter by lazy {
        PlaceRankingListAdapter(
            onClickRankingItem = { ranking -> navigateToPlaceDetailPage(ranking.place.placeName) })
    }

    private val latestPostListAdapter by lazy {
        LatestPostPagingDataAdapter(
            onClickPost = { post -> navigateToPlaceDetailPage(post.placeName) })
    }

    private val placeRankingHorizontalWrapAdapter by lazy {
        PlaceRankingHorizontalWrapAdapter(placeRankingListAdapter)
    }

    private val homeConcatAdapter by lazy {
        ConcatAdapter(placeRankingHorizontalWrapAdapter, latestPostListAdapter)
    }

    private fun navigateToPlacesBySubCityPage(topic: String) {
        navigateWithAction(HomeFragmentDirections.actionHomeFragmentToPlaceListByCityNameFragment(topic))
    }

    private fun navigateToPlaceDetailPage(placeName: String) {
        navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(placeName))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment  = this
        binding.placeRankingListAdapter = placeRankingListAdapter
        binding.latestPostListAdapter   = latestPostListAdapter

        initView()
        observe()
    }

    private fun initView() {
        binding.rvHomeBannerList.apply {
            setHasFixedSize(true)
            adapter = bannerListAdapter
            addItemDecoration(BannerItemDecoration())
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)
        }

        binding.rvHomeRecyclerView.apply {
            setHasFixedSize(true)
            adapter = homeConcatAdapter
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.refreshLatestPostsPagingData.collect { isRefresh ->
                    if (isRefresh) {
                        latestPostListAdapter.refresh()
                        binding.rvHomeRecyclerView.scrollToPosition(0)
                        viewModel.refreshLatestPosts(false)
                    }
                }
            }
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
