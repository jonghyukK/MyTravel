package org.kjh.mytravel.ui.features.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.kjh.mytravel.ui.features.profile.my.ProfileFragment
import org.kjh.mytravel.ui.features.profile.my.ProfileDayLogsFragment
import org.kjh.mytravel.ui.features.profile.user.UserFragment
import org.kjh.mytravel.ui.features.profile.user.UserDayLogsFragment

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
    val fragment: Fragment
): FragmentStateAdapter(fragment) {

    private val profileTabCreators: Map<Int, () -> Fragment> = mapOf(
        DAY_LOGS_GRID_PAGE_INDEX to { ProfileDayLogsFragment.newInstance(DAY_LOGS_GRID_PAGE_INDEX) },
        DAY_LOGS_LINEAR_PAGE_INDEX to { ProfileDayLogsFragment.newInstance(DAY_LOGS_LINEAR_PAGE_INDEX) }
    )

    private val userTabCreators: Map<Int, () -> Fragment> = mapOf(
        DAY_LOGS_GRID_PAGE_INDEX to { UserDayLogsFragment.newInstance(DAY_LOGS_GRID_PAGE_INDEX) },
        DAY_LOGS_LINEAR_PAGE_INDEX to { UserDayLogsFragment.newInstance(DAY_LOGS_LINEAR_PAGE_INDEX) }
    )

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment =
        when (fragment) {
            is ProfileFragment -> profileTabCreators[position]?.invoke()
                ?: throw IndexOutOfBoundsException()
            is UserFragment -> userTabCreators[position]?.invoke()
                ?: throw IndexOutOfBoundsException()
            else -> throw Exception()
        }
}