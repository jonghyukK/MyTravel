package org.kjh.mytravel.ui.features.place

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * MyTravel
 * Class: PlacePagerAdapter
 * Created by jonghyukkang on 2022/01/26.
 *
 * Description:
 */
class PlacePagerAdapter(fragment: PlacePagerFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlaceDayLogFragment.newInstance()
            else -> PlaceInfoFragment.newInstance()
        }
    }
}