package org.kjh.mytravel.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import org.kjh.mytravel.*
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.home.banner.HomeBannersAdapter
import org.kjh.mytravel.home.citycategory.HomeCityCategoriesAdapter
import org.kjh.mytravel.home.event.HomeEventOuterAdapter
import org.kjh.mytravel.home.ranking.PopularRankingListAdapter
import org.kjh.mytravel.uistate.tempBannerItems
import org.kjh.mytravel.uistate.tempCityCategoryItems


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val homeBannersAdapter by lazy {
        HomeBannersAdapter().apply {
            setHasStableIds(true)
        }
    }

    private val rankingListAdapter by lazy {
        PopularRankingListAdapter { item ->
            val action =
                NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
            findNavController().navigate(action)
        }
    }

    private val homeEventListAdapter by lazy {
        HomeEventOuterAdapter { item ->
            val action =
                NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
            findNavController().navigate(action)
        }
    }

    private val recentPlaceAdapter by lazy {
        PlaceListAdapter { item ->
            val action =
                NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbarWithNavigation()
        initHomeBanners()
        initCityCategories()
        initPopularRankingList()
        initEventList()
        initRecentPlaceList()

        viewModel.homeUiState.observe(viewLifecycleOwner, { uiState ->
            homeBannersAdapter.submitList(uiState.bannerItems)
            rankingListAdapter.submitList(uiState.rankingItems)
            homeEventListAdapter.submitList(uiState.eventItems)
            recentPlaceAdapter.submitList(uiState.recentPlaceItems)
        })
    }

    override fun onResume() {
        super.onResume()

        if (binding.rvEventList.adapter == null) {
            binding.rvHomeBanners.adapter        = homeBannersAdapter
            binding.rvPopularRankingList.adapter = rankingListAdapter
            binding.rvEventList.adapter          = homeEventListAdapter
            binding.rvRecentPlaceList.adapter    = recentPlaceAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding.rvHomeBanners.adapter        = null
        binding.rvPopularRankingList.adapter = null
        binding.rvEventList.adapter          = null
        binding.rvRecentPlaceList.adapter    = null
    }

    private fun initToolbarWithNavigation() {
        val appConfiguration = (requireActivity() as MainActivity).appBarConfiguration
        binding.tbHomeToolbar.setupWithNavController(findNavController(), appConfiguration)
    }

    /***************************************************************
     *  Home Banners
     ***************************************************************/
    private fun initHomeBanners() {
        binding.rvHomeBanners.apply {
            adapter = homeBannersAdapter
            setHasFixedSize(true)
            addItemDecoration(
                LinearLayoutItemDecorationWithTextIndicator(
                    requireContext(), 0, 0, 0, 0)
            )

            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.rvHomeBanners)

            addOnScrollListener(object : OnScrollListener() {
                val linearLayoutManager = (this@apply.layoutManager as LinearLayoutManager)

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val firstItemVisible: Int = linearLayoutManager.findFirstVisibleItemPosition()

                    if (firstItemVisible != 1 && (firstItemVisible % tempBannerItems.size == 1)) {
                        linearLayoutManager.scrollToPosition(1)
                    } else if (firstItemVisible == 0) {
                        linearLayoutManager.scrollToPositionWithOffset(
                            tempBannerItems.size,
                            -recyclerView.computeHorizontalScrollOffset()
                        )
                    }
                }
            })
        }
    }

    /***************************************************************
     *  Home City Categories
     ***************************************************************/
    private fun initCityCategories() {
        binding.rvCityCategoryList.apply {
            adapter = HomeCityCategoriesAdapter(tempCityCategoryItems) { item ->
                val action = HomeFragmentDirections.actionHomeFragmentToHomeSpecificCityPagerFragment(item.cityName)
                findNavController().navigate(action)
            }
            setHasFixedSize(true)
            addItemDecoration(
                LinearLayoutItemDecoration(
                    requireContext(), 10, 10, 0, 15)
            )
        }
    }

    /***************************************************************
     *  Home Popular Place Ranking
     ***************************************************************/
    private fun initPopularRankingList() {
        binding.rvPopularRankingList.apply {
            adapter = rankingListAdapter
            addItemDecoration(
                LinearLayoutItemDecoration(
                    this.context, 20, 20, 0, 20)
            )
            setHasFixedSize(true)

            val pageSnapHelper = PagerSnapHelper()
            pageSnapHelper.attachToRecyclerView(this)
        }
    }

    /***************************************************************
     *  Home Event List
     ***************************************************************/
    private fun initEventList() {
        binding.rvEventList.apply {
            adapter = homeEventListAdapter
        }
    }

    /***************************************************************
     *  Recent Place List
     ***************************************************************/
    private fun initRecentPlaceList() {
        binding.rvRecentPlaceList.apply {
            adapter = recentPlaceAdapter
        }
    }
}