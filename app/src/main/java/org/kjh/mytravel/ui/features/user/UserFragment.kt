package org.kjh.mytravel.ui.features.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.features.profile.MyProfileViewModel
import org.kjh.mytravel.ui.base.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment
    : BaseFragment<FragmentUserBinding>(R.layout.fragment_user){

    @Inject
    lateinit var userViewModelFactory: UserViewModel.UserNameAssistedFactory

    private val viewModel: UserViewModel by viewModels {
        UserViewModel.provideFactory(userViewModelFactory, args.userEmail)
    }

    private val args: UserFragmentArgs by navArgs()
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val postSmallListAdapter by lazy {
        UserPostListAdapter(
            onClickPost     = ::navigateToPlaceDetailPage,
            onClickBookmark = ::requestBookmarkStateUpdate
        )
    }

    private fun navigateToPlaceDetailPage(post: Post) {
        navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(post.placeName))
    }

    private fun requestBookmarkStateUpdate(post: Post) {
        myProfileViewModel.updateBookmark(post.postId, post.placeName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.fragment  = this
        binding.myProfileViewModel = myProfileViewModel

        initView()
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        uiState.isError?.let {
                            showError(it)
                            viewModel.shownErrorToast()
                        }
                    }
                }

                launch {
                    viewModel.followState.collect { followState ->
                        when (followState) {
                            is FollowState.Success -> {
                                myProfileViewModel.updateMyProfile(followState.followItem.myProfile)
                            }
                            is FollowState.Error -> {
                                followState.error?.let {
                                    showError(it)
                                    viewModel.initFollowState()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.tbUserToolbar.setupWithNavController(findNavController())

        binding.rvUserPostList.apply {
            adapter = postSmallListAdapter
            setHasFixedSize(true)
            addItemDecoration(UserPostsGridItemDecoration(requireContext()))
        }
    }

    fun requestUpdateFollowStateWithUser() {
        viewModel.makeRequestFollow(targetEmail = args.userEmail)
    }
}