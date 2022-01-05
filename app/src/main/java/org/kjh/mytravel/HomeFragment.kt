package org.kjh.mytravel

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
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.databinding.ItemRecentVisitBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val homeEventListAdapter by lazy {
        HomeEventListOuterAdapter(eventItemList) { item ->
            val action =
                HomeFragmentDirections.actionHomeFragmentToCityDetailFragment(item.cityName)
            findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
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
    }

    override fun onResume() {
        super.onResume()

        if (binding.rvEventList.adapter == null) {
            binding.rvEventList.adapter = homeEventListAdapter
        }
    }

    override fun onStop() {
        super.onStop()
        binding.rvEventList.adapter = null
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
            adapter = HomeBannersAdapter(cityItemList).apply {
                setHasStableIds(true)
            }
            setHasFixedSize(true)
            addItemDecoration(
                LinearLayoutItemDecorationWithTextIndicator(
                    requireContext(), 0, 0, 0, 0))

            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.rvHomeBanners)

            addOnScrollListener(object : OnScrollListener() {
                val linearLayoutManager = (this@apply.layoutManager as LinearLayoutManager)

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val firstItemVisible: Int = linearLayoutManager.findFirstVisibleItemPosition()

                    if (firstItemVisible != 1 && (firstItemVisible % cityItemList.size == 1)) {
                        linearLayoutManager.scrollToPosition(1)
                    } else if (firstItemVisible == 0) {
                        linearLayoutManager.scrollToPositionWithOffset(
                            cityItemList.size,
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
            adapter = HomeCityCategoriesAdapter(cityItemList)
            setHasFixedSize(true)
            addItemDecoration(
                LinearLayoutItemDecoration(
                    requireContext(), 10, 10, 0, 15))
        }
    }


    /***************************************************************
     *  Home Popular Place Ranking
     ***************************************************************/
    private fun initPopularRankingList() {
        binding.rvPopularRankingList.apply {
            adapter = PopularRankingListAdapter(cityItemList)
            addItemDecoration(
                LinearLayoutItemDecoration(
                    this.context, 20, 20, 0, 20))
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
            setHasFixedSize(true)
        }
    }


    /***************************************************************
     *  Recent Place List
     ***************************************************************/
    private fun initRecentPlaceList() {
        binding.rvRecentPlaceList.apply {
            adapter = RecentPlaceListAdapter(eventItemList)
            setHasFixedSize(true)
        }
    }
}


class RecentPlaceListAdapter(
    private val recentList: List<EventItem>
): RecyclerView.Adapter<RecentPlaceListAdapter.RecentPlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPlaceViewHolder {
        return RecentPlaceViewHolder(
            ItemRecentVisitBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecentPlaceViewHolder, position: Int) {
        holder.bind(recentList[position])
    }

    override fun getItemCount() = recentList.size

    class RecentPlaceViewHolder(
        val binding: ItemRecentVisitBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EventItem) {
            binding.eventItem = item
        }
    }
}
