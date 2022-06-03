package org.kjh.mytravel.ui.features.place.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IndexOutOfBoundsException

/**
 * MyTravel
 * Class: PlacePagerAdapter
 * Created by jonghyukkang on 2022/01/26.
 *
 * Description:
 */

const val PLACE_DAY_LOG_PAGE_INDEX = 0
const val PLACE_INFO_PAGE_INDEX = 1

class PlacePagerAdapter(
    fragment: PlacePagerFragment
) : FragmentStateAdapter(fragment) {

    private val placeTabCreators: Map<Int, () -> Fragment> = mapOf(
        PLACE_DAY_LOG_PAGE_INDEX to { PlaceDayLogFragment.newInstance() },
        PLACE_INFO_PAGE_INDEX to {PlaceInfoFragment.newInstance() }
    )

    override fun getItemCount(): Int = placeTabCreators.size

    override fun createFragment(position: Int): Fragment =
        placeTabCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}