package org.kjh.mytravel.ui.features.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.kjh.mytravel.ui.features.profile.my.ProfileFragment
import org.kjh.mytravel.ui.features.profile.my.ProfilePostsFragment
import org.kjh.mytravel.ui.features.profile.user.UserFragment
import org.kjh.mytravel.ui.features.profile.user.UserPostsFragment
import org.kjh.mytravel.ui.features.profile.user.UserPostsFragment.Companion.TYPE_GRID
import org.kjh.mytravel.ui.features.profile.user.UserPostsFragment.Companion.TYPE_LINEAR

/**
 * MyTravel
 * Class: UserTabPagerAdapter
 * Created by jonghyukkang on 2022/05/30.
 *
 * Description:
 */
class PostsTabPagerAdapter(
    val fragment: Fragment
): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val userTabFragmentList = listOf(
            UserPostsFragment.newInstance(TYPE_GRID),
            UserPostsFragment.newInstance(TYPE_LINEAR)
        )

        val profileTabFragmentList = listOf(
            ProfilePostsFragment.newInstance(ProfilePostsFragment.TYPE_GRID),
            ProfilePostsFragment.newInstance(ProfilePostsFragment.TYPE_LINEAR)
        )

        return when (getFragmentType(fragment)) {
            is FragmentType.User -> userTabFragmentList[position]
            is FragmentType.Profile -> profileTabFragmentList[position]
        }
    }

    private fun getFragmentType(fragment: Fragment) = when (fragment) {
        is UserFragment -> FragmentType.User
        is ProfileFragment -> FragmentType.Profile
        else -> throw Exception("Wrong Fragment in This Adapter.")
    }

    sealed class FragmentType {
        object Profile: FragmentType()
        object User   : FragmentType()
    }
}