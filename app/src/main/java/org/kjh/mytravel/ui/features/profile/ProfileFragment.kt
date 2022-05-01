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


interface ProfileClickEvent {
    fun onClickPost(item: Post)
    fun onClickBookmark(item: Post)
    fun onClickProfileEdit(myProfileItem: User)
    fun onClickWritePost()
    fun onClickSettings()
}

@AndroidEntryPoint
class ProfileFragment
    :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile), ProfileClickEvent {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val myPostListAdapter by lazy {
        MyPostListAdapter(
            onClickPost     = { post -> onClickPost(post)},
            onClickBookmark = { post -> onClickBookmark(post)}
        )
    }

    override fun onClickPost(item: Post) {
        navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
    }

    override fun onClickBookmark(item: Post) {
        myProfileViewModel.updateBookmark(item.postId, item.placeName)
    }

    override fun onClickProfileEdit(myProfileItem: User) {
        navigateWithAction(
            ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(
                myProfileItem.profileImg, myProfileItem.nickName, myProfileItem.introduce
            )
        )
    }

    override fun onClickWritePost() {
        navigateWithAction(ProfileFragmentDirections.actionProfileFragmentToSelectPhotoFragment())
    }

    override fun onClickSettings() {
        navigateWithAction(ProfileFragmentDirections.actionProfileFragmentToSettingFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val navOptions =
                NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setRestoreState(true)
                    .setPopUpTo(
                        findNavController().graph.findStartDestination().id,
                        inclusive = false,
                        saveState = true
                    ).build()
            findNavController().navigate(R.id.home, null, navOptions)
        }
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
            setOnMenuItemClickListener {
                when (it.itemId) {
                    // 글쓰기
                    R.id.write_post -> {
                        onClickWritePost()
                        true
                    }
                    // 설정
                    R.id.settings -> {
                        onClickSettings()
                        true
                    }
                    else -> false
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
                        navigateWithAction(
                            ProfileFragmentDirections.actionProfileFragmentToNotLoginFragment()
                        )
                    }
                }
            }
        }
    }
}