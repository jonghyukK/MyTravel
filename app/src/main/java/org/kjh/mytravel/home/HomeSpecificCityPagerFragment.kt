package org.kjh.mytravel.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.kjh.mytravel.cityItemList
import org.kjh.mytravel.databinding.FragmentHomeSpecificCityPagerBinding


class HomeSpecificCityPagerFragment : Fragment() {
    private lateinit var binding: FragmentHomeSpecificCityPagerBinding
    private val args: HomeSpecificCityPagerFragmentArgs by navArgs()

    var initPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            initPos = cityItemList.indexOf(cityItemList.find { it.cityName == args.cityName })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeSpecificCityPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbSpecificCityToolbar.setupWithNavController(findNavController())

        val cityAdapter = EachCityPagerAdapter(this)
        binding.vpCityPager.adapter = cityAdapter

        TabLayoutMediator(binding.tlCityTabLayout, binding.vpCityPager) { tab, position ->
            tab.text = cityItemList[position].cityName
        }.attach()

        if (savedInstanceState == null) {
            binding.vpCityPager.setCurrentItem(initPos, false)
            binding.tlCityTabLayout.getTabAt(initPos)?.select()
        }
    }

    private inner class EachCityPagerAdapter(
        frag: HomeSpecificCityPagerFragment
    ): FragmentStateAdapter(frag) {
        override fun getItemCount() = cityItemList.size

        override fun createFragment(position: Int): Fragment {
            return HomeSpecificCityListFragment.newInstance(cityItemList[position].cityName)
        }
    }
}