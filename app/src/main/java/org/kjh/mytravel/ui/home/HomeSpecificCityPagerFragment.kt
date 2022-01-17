package org.kjh.mytravel.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.kjh.mytravel.databinding.FragmentHomeSpecificCityPagerBinding
import org.kjh.mytravel.ui.uistate.tempCityCategoryItems


class HomeSpecificCityPagerFragment : Fragment() {
    private lateinit var binding: FragmentHomeSpecificCityPagerBinding
    private val args: HomeSpecificCityPagerFragmentArgs by navArgs()

    var initPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            initPos = tempCityCategoryItems.indexOf(tempCityCategoryItems.find { it.cityName == args.cityName })
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
            tab.text = tempCityCategoryItems[position].cityName
        }.attach()

        if (savedInstanceState == null) {
            binding.vpCityPager.setCurrentItem(initPos, false)
            binding.tlCityTabLayout.getTabAt(initPos)?.select()
        }
    }

    private inner class EachCityPagerAdapter(
        frag: HomeSpecificCityPagerFragment
    ): FragmentStateAdapter(frag) {
        override fun getItemCount() = tempCityCategoryItems.size

        override fun createFragment(position: Int): Fragment {
            return HomeSpecificCityListFragment.newInstance(tempCityCategoryItems[position].cityName)
        }
    }
}