package org.kjh.mytravel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.*
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.ui.MainActivity
import org.kjh.mytravel.ui.PlaceListAdapter
import org.kjh.mytravel.ui.home.banner.HomeBannersAdapter
import org.kjh.mytravel.ui.home.citycategory.HomeCityCategoriesAdapter
import org.kjh.mytravel.ui.home.event.HomeEventOuterAdapter
import org.kjh.mytravel.ui.home.ranking.PopularRankingListAdapter
import org.kjh.mytravel.ui.uistate.tempBannerItems
import org.kjh.mytravel.ui.uistate.tempCityCategoryItems


@AndroidEntryPoint
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bannerUiState.collect {
                    when (it) {
                        is BannerUiState.Success -> homeBannersAdapter.submitList(it.banners)
                        is BannerUiState.Error -> Toast.makeText(requireContext(), "Banner Error!!!!!!!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rankingUiState.collect {
                    when (it) {
                        is RankingUiState.Success -> rankingListAdapter.submitList(it.rankingItems)
                        is RankingUiState.Error -> Toast.makeText(requireContext(), "Ranking Error!!!!!!!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventUiState.collect {
                    when (it) {
                        is EventUiState.Success -> homeEventListAdapter.submitList(it.eventItems)
                        is EventUiState.Error -> Toast.makeText(requireContext(), "Event Error!!!!!!!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentPlaceUiState.collect {
                    when (it) {
                        is RecentPlaceUiState.Success -> recentPlaceAdapter.submitList(it.recentPlaceItems)
                        is RecentPlaceUiState.Error -> Toast.makeText(requireContext(), "Recent Error!!!!!!!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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