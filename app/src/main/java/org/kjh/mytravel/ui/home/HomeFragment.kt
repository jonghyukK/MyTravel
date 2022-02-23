package org.kjh.mytravel.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.*
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.ui.MainActivity
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.home.ranking.PlaceRankingListAdapter
import org.kjh.mytravel.ui.home.ranking.PlaceRankingListOuterAdapter


@AndroidEntryPoint
class HomeFragment
    : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val homeConcatAdapter by lazy {
        ConcatAdapter(PlaceRankingListOuterAdapter(placeRankingAdapter), recentPlaceAdapter)
    }

    private val placeRankingAdapter by lazy {
        PlaceRankingListAdapter { item ->
            navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(item.place.placeName))
        }
    }

    private val recentPlaceAdapter by lazy {
        RecentPlaceListAdapter { item ->
            navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbarWithNavigation()
        initHomeRecyclerView()

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

    private fun initHomeRecyclerView() {
        binding.rvHomeRecyclerView.apply {
            adapter = homeConcatAdapter
        }
    }

    private fun initToolbarWithNavigation() {
        val appConfiguration = (requireActivity() as MainActivity).appBarConfiguration
        binding.tbHomeToolbar.setupWithNavController(findNavController(), appConfiguration)
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

    //    private val homeBannersAdapter by lazy {
//        HomeBannersAdapter().apply {
//            setHasStableIds(true)
//        }
//    }
//

//    private val homeEventListAdapter by lazy {
//        HomeEventOuterAdapter { item ->
//            val action =
//                NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
//            findNavController().navigate(action)
//        }
//    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        initToolbarWithNavigation()
//        initHomeBanners()
//        initCityCategories()
//        initPopularRankingList()
//        initEventList()
//        initRecentPlaceList()
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.bannerUiState.collect {
//                    when (it) {
//                        is BannerUiState.Success -> homeBannersAdapter.submitList(it.banners)
//                        is BannerUiState.Error -> Toast.makeText(requireContext(), "Banner Error!!!!!!!!!", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.eventUiState.collect {
//                    when (it) {
//                        is EventUiState.Success -> homeEventListAdapter.submitList(it.eventItems)
//                        is EventUiState.Error -> Toast.makeText(requireContext(), "Event Error!!!!!!!!!", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }

//
//    /***************************************************************
//     *  Home Banners
//     ***************************************************************/
//    private fun initHomeBanners() {
//        binding.rvHomeBanners.apply {
//            adapter = homeBannersAdapter
//            setHasFixedSize(true)
//            addItemDecoration(
//                LinearLayoutItemDecorationWithTextIndicator(
//                    requireContext(), 0, 0, 0, 0)
//            )
//
//            val pagerSnapHelper = PagerSnapHelper()
//            pagerSnapHelper.attachToRecyclerView(binding.rvHomeBanners)
//
//            addOnScrollListener(object : OnScrollListener() {
//                val linearLayoutManager = (this@apply.layoutManager as LinearLayoutManager)
//
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    val firstItemVisible: Int = linearLayoutManager.findFirstVisibleItemPosition()
//
//                    if (firstItemVisible != 1 && (firstItemVisible % tempBannerItems.size == 1)) {
//                        linearLayoutManager.scrollToPosition(1)
//                    } else if (firstItemVisible == 0) {
//                        linearLayoutManager.scrollToPositionWithOffset(
//                            tempBannerItems.size,
//                            -recyclerView.computeHorizontalScrollOffset()
//                        )
//                    }
//                }
//            })
//        }
//    }
//
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

//    /***************************************************************
//     *  Home Event List
//     ***************************************************************/
//    private fun initEventList() {
//        binding.rvEventList.apply {
//            adapter = homeEventListAdapter
//        }
//    }
}