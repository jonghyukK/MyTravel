package org.kjh.mytravel.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.View.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.*
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.ui.MainActivity
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.home.banner.HomeBannerItemDecoration
import org.kjh.mytravel.ui.home.banner.HomeBannersAdapter
import org.kjh.mytravel.ui.home.ranking.PlaceRankingListAdapter
import org.kjh.mytravel.ui.home.ranking.PlaceRankingListOuterAdapter


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    // Home "Banner" Adapter.
    private val homeBannerAdapter by lazy {
        HomeBannersAdapter().apply {
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

    private var bannerCount = 0

    override fun onDestroyView() {
        binding.rvHomeRecyclerView.adapter = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initToolbarWithNavigation()
        initHomeBannerRecyclerView()
        initHomeRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeBannersUiString.collect {
                    homeBannerAdapter.submitList(it.homeBannerItems)
                    bannerCount = it.homeBannerItems.size
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.placeRankingUiState.collect {
                    placeRankingAdapter.submitList(it.placeRankingItems)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentPostsUiState.collect {
                    it.recentPlaceItems?.let { pagingData ->
                        recentPlaceAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }

    private fun navigateWithAction(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun initHomeBannerRecyclerView() {
        binding.rvHomeBannerList.apply {
            adapter = homeBannerAdapter
            setHasFixedSize(true)

            addItemDecoration(HomeBannerItemDecoration())

            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.rvHomeBannerList)

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

    private fun initHomeRecyclerView() {
        binding.rvHomeRecyclerView.apply {
            adapter = homeConcatAdapter
        }
    }

    private fun initToolbarWithNavigation() {
        val appConfiguration = (requireActivity() as MainActivity).appBarConfiguration
        binding.tbHomeToolbar.setupWithNavController(findNavController(), appConfiguration)
        binding.tbHomeToolbar.title = "Travel"

        var scrollRange = -1
        binding.ablHomeAppBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = appBarLayout?.totalScrollRange!!
                }

                val range = (255.toDouble() / appBarLayout.totalScrollRange.toDouble())
//                binding.rvHomeBannerList.imageAlpha = (255 + (range * verticalOffset.toFloat())).toInt()

                val isCollapsed = scrollRange + verticalOffset == 0

                binding.ctlHomeCollapsingToolbar.setStatusBarScrimColor(
                    if (isCollapsed) Color.WHITE else Color.TRANSPARENT)

                binding.tbHomeToolbar.setTitleTextColor(
                    if (isCollapsed) Color.BLACK else Color.WHITE)

                binding.tbHomeToolbar.setBackgroundColor(
                    if (isCollapsed) Color.WHITE else Color.TRANSPARENT)
            })
    }

    override fun onResume() {
        super.onResume()
        if (binding.rvHomeRecyclerView.adapter == null) {
            binding.rvHomeRecyclerView.adapter = homeConcatAdapter
        }
    }



//    /***************************************************************
//     *  Home City Categories
//     ***************************************************************/
//    private fun initCityCategories() {
//        binding.rvCityCategoryList.apply {
//            adapter = HomeCityCategoriesAdapter(tempCityCategoryItems) { item ->
//                val action = HomeFragmentDirections.actionHomeFragmentToHomeSpecificCityPagerFragment(item.cityName)
//                findNavController().navigate(action)
//            }
//            setHasFixedSize(true)
//            addItemDecoration(
//                LinearLayoutItemDecoration(
//                    requireContext(), 10, 10, 0, 15)
//            )
//        }
//    }
}