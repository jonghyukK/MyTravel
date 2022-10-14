package org.kjh.mytravel.ui.features.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.kjh.mytravel.ui.features.profile.my.ProfileFragment
import org.kjh.mytravel.ui.features.profile.user.UserFragment

/**
 * MyTravel
 * Class: UserTabPagerAdapter
 * Created by jonghyukkang on 2022/05/30.
 *
 * Description:
 */

const val DAY_LOGS_GRID_PAGE_INDEX = 0
const val DAY_LOGS_LINEAR_PAGE_INDEX = 1

class DayLogsTabPagerAdapter(
    private val fragment: Fragment,
    private val isMine  : Boolean
): FragmentStateAdapter(fragment) {

    private val tabCreator: Map<Int, () -> Fragment> = mapOf(
        DAY_LOGS_GRID_PAGE_INDEX to
                { DayLogsRecyclerViewFragment.newInstance(DAY_LOGS_GRID_PAGE_INDEX, isMine) },
        DAY_LOGS_LINEAR_PAGE_INDEX to
                { DayLogsRecyclerViewFragment.newInstance(DAY_LOGS_LINEAR_PAGE_INDEX, isMine) }
    )

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment =
        tabCreator[position]?.invoke()
            ?: throw Exception("Not Found Fragment in TabCreator by position")
}