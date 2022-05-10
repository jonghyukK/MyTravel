package org.kjh.mytravel.ui.features.profile

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.features.profile.MyProfileViewModel
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.utils.onThrottleMenuItemClick


@AndroidEntryPoint
class ProfileFragment
    :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val myPostListAdapter by lazy {
        MyPostListAdapter(
            onClickPost     = { post -> onClickPost(post)},
            onClickBookmark = { post -> onClickBookmark(post)}
        )
    }

    private fun onClickPost(item: Post) {
        navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
    }

    private fun onClickBookmark(item: Post) {
        myProfileViewModel.updateBookmark(item.postId, item.placeName)
    }

    fun onClickProfileEdit(myProfileItem: User) {
        navigateWithAction(
            ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(
                myProfileItem.profileImg, myProfileItem.nickName, myProfileItem.introduce
            )
        )
    }

    private fun onClickWritePost() {
        navigateWithAction(ProfileFragmentDirections.actionProfileFragmentToSelectPhotoFragment())
    }

    private fun onClickSettings() {
        navigateWithAction(ProfileFragmentDirections.actionProfileFragmentToSettingFragment())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.myProfileViewModel = myProfileViewModel

        initView()
        observe()
    }

    private fun initView() {
        binding.tbProfileToolbar.apply {
            inflateMenu(R.menu.menu_profile)
            onThrottleMenuItemClick {
                when (it.itemId) {
                    R.id.write_post -> onClickWritePost()
                    R.id.settings   -> onClickSettings()
                }
            }
        }

        binding.rvMyPostList.apply {
            setHasFixedSize(true)
            adapter = myPostListAdapter
            addItemDecoration(ProfilePostsGridItemDecoration(requireContext()))
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myProfileViewModel.isLoggedIn.collect { isLoggedIn ->
                    if (!isLoggedIn) {
                        navigateToLoginPageWhenNotLogin()
                    }
                }
            }
        }
    }

    private fun navigateToLoginPageWhenNotLogin() {
        navigateWithAction(ProfileFragmentDirections.actionProfileFragmentToNotLoginFragment())
    }
}