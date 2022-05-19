package org.kjh.mytravel.ui.features.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.model.Follow
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.common.UiState
import org.kjh.mytravel.ui.features.profile.MyProfileViewModel
import org.kjh.mytravel.utils.navigateToPlaceDetail
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment
    : BaseFragment<FragmentUserBinding>(R.layout.fragment_user){

    @Inject
    lateinit var userViewModelFactory: UserViewModel.UserNameAssistedFactory

    private val args: UserFragmentArgs by navArgs()
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val viewModel: UserViewModel by viewModels {
        UserViewModel.provideFactory(userViewModelFactory, args.userEmail)
    }

    private val userPostListAdapter by lazy {
        UserPostListAdapter(
            onClickPost     = { post -> navigateToPlaceDetail(post.placeName) },
            onClickBookmark = ::requestUpdateBookmark
        )
    }

    private fun requestUpdateBookmark(post: Post) {
        myProfileViewModel.updateBookmark(post.postId, post.placeName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment  = this

        initToolbarWithNavigation()
        initUserPostsRecyclerView()

        observe()
    }

    private fun initToolbarWithNavigation() {
        binding.tbUserToolbar.setupWithNavController(findNavController())
    }

    private fun initUserPostsRecyclerView() {
        binding.rvUserPostList.apply {
            setHasFixedSize(true)
            adapter = userPostListAdapter
            addItemDecoration(UserPostsGridItemDecoration(requireContext()))
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    myProfileViewModel.myProfileState.collect { state ->
                        viewModel.updatePostsWithBookmark(state.myBookmarkItems)
                    }
                }

                launch {
                    viewModel.followState.collect { state ->
                        if (state is UiState.Success) {
                            myProfileViewModel.updateMyProfile(state.data.myProfile)
                            viewModel.initFollowState()
                        }
                    }
                }
            }
        }
    }
}