package org.kjh.mytravel.ui.home

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
import org.kjh.mytravel.ui.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.home.banner.HomeBannerItemDecoration
import org.kjh.mytravel.ui.home.latest.LatestPostListAdapter
import org.kjh.mytravel.ui.home.ranking.PlaceRankingHorizontalWrapAdapter
import org.kjh.mytravel.ui.home.ranking.PlaceRankingListAdapter

interface HomeClickEvent {
    fun onClickBannerItem(topic: String)
    fun onClickRankingItem(placeName: String)
    fun onClickLatestPostItem(placeName: String)
}

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), HomeClickEvent {

    private val viewModel: HomeViewModel by activityViewModels()

    // Adapter for "Banners"
    private val bannerListAdapter by lazy {
        BannerListAdapter { item -> onClickBannerItem(item.bannerTopic) }
    }

    // Adapter for "Rankings"
    private val placeRankingListAdapter by lazy {
        PlaceRankingListAdapter { item -> onClickRankingItem(item.place.placeName) }
    }

    // Adapter for "Rankings" that Horizontal Wrapper.
    private val placeRankingHorizontalWrapAdapter by lazy {
        PlaceRankingHorizontalWrapAdapter(placeRankingListAdapter)
    }

    // Adapter for "Latest Posts"
    private val latestPostListAdapter by lazy {
        LatestPostListAdapter { item -> onClickLatestPostItem(item.placeName) }
    }

    // Concat Adapter for [Ranking, Latest Posts].
    private val homeConcatAdapter by lazy {
        ConcatAdapter(placeRankingHorizontalWrapAdapter, latestPostListAdapter)
    }

    override fun onClickBannerItem(topic: String) {
        navigateWithAction(HomeFragmentDirections.actionHomeFragmentToPlaceListByCityNameFragment(topic))
    }

    override fun onClickRankingItem(placeName: String) {
        navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(placeName))
    }

    override fun onClickLatestPostItem(placeName: String) {
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
            addItemDecoration(HomeBannerItemDecoration())
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
