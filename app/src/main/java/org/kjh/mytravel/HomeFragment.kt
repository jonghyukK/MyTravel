package org.kjh.mytravel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import org.kjh.mytravel.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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
        initHomeList()
    }

    private fun initToolbarWithNavigation() {
        val appConfiguration = (requireActivity() as MainActivity).appBarConfiguration
        binding.tbHomeToolbar.setupWithNavController(findNavController(), appConfiguration)
    }

    private fun initHomeBanners() {
        val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        binding.rvHomeBanners.apply {
            adapter = HomeBannersAdapter(cityItemList).apply {
                setHasStableIds(true)
            }
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            addItemDecoration(LinearLayoutItemDecoration(requireContext(), 0, 0, 0, 0))

            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.rvHomeBanners)

            addOnScrollListener(object : OnScrollListener() {
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

    private fun initHomeList() {
        binding.rvCityList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = CityListAdapter(cityItemList, viewType = 1) { item ->
                val action = HomeFragmentDirections.actionHomeFragmentToCityDetailFragment(item.cityName)
                findNavController().navigate(action)
            }
            addItemDecoration(GridLayoutItemDecoration(this.context))
        }
    }
}