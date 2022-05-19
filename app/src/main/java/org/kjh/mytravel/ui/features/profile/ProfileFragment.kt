package org.kjh.mytravel.ui.features.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.model.User
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.utils.navigateTo
import org.kjh.mytravel.utils.navigateToPlaceDetail
import org.kjh.mytravel.utils.onThrottleMenuItemClick


@AndroidEntryPoint
class ProfileFragment
    :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val myPostListAdapter by lazy {
        MyPostListAdapter(
            onClickPost     = { post -> navigateToPlaceDetail(post.placeName)},
            onClickBookmark = ::requestUpdateBookmark
        )
    }

    private fun requestUpdateBookmark(post: Post) {
        myProfileViewModel.updateBookmark(post.postId, post.placeName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        binding.myProfileViewModel = myProfileViewModel

        initToolbarWithMenu()
        initMyPostsRecyclerView()

        observeLoginState()
    }

    private fun initToolbarWithMenu() {
        binding.tbProfileToolbar.apply {
            inflateMenu(R.menu.menu_profile)
            onThrottleMenuItemClick { menuItem ->
                when (menuItem.itemId) {
                    R.id.write_post -> navigateTo(ProfileFragmentDirections.actionToSelectPhoto())
                    R.id.settings   -> navigateTo(ProfileFragmentDirections.actionToSetting())
                }
            }
        }
    }

    private fun initMyPostsRecyclerView() {
        binding.myPostsRecyclerView.apply {
            setHasFixedSize(true)
            adapter = myPostListAdapter
            addItemDecoration(ProfilePostsGridItemDecoration(requireContext()))
        }
    }

    private fun observeLoginState() {
        myProfileViewModel.isLoggedIn
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { isLoggedIn ->
                if (!isLoggedIn) {
                    navigateTo(ProfileFragmentDirections.actionToNotLogin())
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    fun navigateToProfileEditPage(myProfileItem: User) {
        navigateTo(
            ProfileFragmentDirections.actionToProfileEdit(
                myProfileItem.profileImg, myProfileItem.nickName, myProfileItem.introduce
            )
        )
    }
}