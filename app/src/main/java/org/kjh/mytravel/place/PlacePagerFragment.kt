package org.kjh.mytravel.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.kjh.mytravel.databinding.FragmentPlacePagerBinding

val TAB_TITLE = listOf("데이로그", "정보")

class PlacePagerFragment : Fragment() {

    private lateinit var binding: FragmentPlacePagerBinding
    private val args: PlacePagerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlacePagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = args.cityName

        initToolbarWithAppBarLayout()
        initTabLayoutWithPager()
    }

    private fun initToolbarWithAppBarLayout() {
        binding.tbPlacePagerToolbar.setupWithNavController(findNavController())

        var scrollRange = -1
        binding.abl.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }

            binding.ctl.title = if (scrollRange + verticalOffset == 0) args.cityName else " "
        })
    }

    private fun initTabLayoutWithPager() {
        binding.pager.apply {
            adapter = PlacePagerAdapter(this@PlacePagerFragment)
            isUserInputEnabled = false
        }

        TabLayoutMediator(binding.tlTabLayout, binding.pager) { tab, position ->
            tab.text = TAB_TITLE[position]
        }.attach()
    }
}

class PlacePagerAdapter(fragment: PlacePagerFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
                0 -> PlaceDayLogFragment.newInstance()
            else -> PlaceInfoFragment.newInstance("", "")
        }
    }
}