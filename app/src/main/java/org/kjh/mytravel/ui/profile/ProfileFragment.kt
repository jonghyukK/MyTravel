package org.kjh.mytravel.ui.profile

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.MyProfileViewModel
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.ProfilePostsGridItemDecoration
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.PostSmallListAdapter
import org.kjh.mytravel.ui.base.BaseFragment


@AndroidEntryPoint
class ProfileFragment
    :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val myPostListAdapter by lazy {
        PostSmallListAdapter(
            onClickPost     = { post -> onClickPost(post)},
            onClickBookmark = { post -> onClickBookmark(post)}
        )
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

        initToolbarWithNavigation()
        initMyPostRecyclerView()

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myProfileViewModel.isError.collectLatest {
                    it?.let { showError(it) }
                }
            }
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbProfileToolbar.apply {
            inflateMenu(R.menu.menu_profile)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    // 글쓰기
                    R.id.write_post -> {
                        navigateWithAction(
                            ProfileFragmentDirections.actionProfileFragmentToSelectPhotoFragment())
                        true
                    }
                    // 설정
                    R.id.settings -> {
                        navigateWithAction(
                            ProfileFragmentDirections.actionProfileFragmentToSettingFragment())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initMyPostRecyclerView() {
        binding.rvMyPostList.apply {
            setHasFixedSize(true)
            adapter = myPostListAdapter
            addItemDecoration(ProfilePostsGridItemDecoration(requireContext()))
        }
    }

    private fun onClickPost(item: Post) {
        navigateWithAction(
            NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
    }

    private fun onClickBookmark(item: Post) {
        myProfileViewModel.updateBookmark(item.postId, item.placeName)
    }

    fun onClickProfileEdit(v: View) {
        val myProfileData = myProfileViewModel.myProfileState.value!!

        navigateWithAction(
            ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment(
                myProfileData.profileImg,
                myProfileData.nickName,
                myProfileData.introduce
            )
        )
    }
}