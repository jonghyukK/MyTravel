package org.kjh.mytravel.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.domain.entity.Post
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.ProfilePostsGridItemDecoration
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.ui.PostSmallListAdapter
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.profile.ProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment
    : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {

    private val args: UserFragmentArgs by navArgs()

    @Inject
    lateinit var userViewModelFactory: UserViewModel.UserNameAssistedFactory

    private val viewModel: UserViewModel by viewModels {
        UserViewModel.provideFactory(userViewModelFactory, args.userEmail)
    }
    private val profileViewModel: ProfileViewModel by activityViewModels()

    private val postSmallListAdapter by lazy {
        PostSmallListAdapter({ item -> onClickPostItem(item)}) { item ->
            viewModel.updateBookmark(item)
            profileViewModel.updateBookmarkStateWithPosts(item)
        }
    }

    private fun onClickPostItem(item: Post) {
        val action = NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        initToolbarWithNavigation()
        initUserPostsRecyclerView()
        initClickEvents()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.userItem?.let {
                        postSmallListAdapter.submitList(uiState.userItem.posts)
                    }

                    if (uiState.successFollowOrNot) {
                        profileViewModel.uiState.value.userItem?.let {
                            profileViewModel.updateProfileData(
                                it.copy(
                                    followingCount = if (uiState.userItem?.isFollowing!!) {
                                        it.followingCount + 1
                                    } else {
                                        it.followingCount - 1
                                    }
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initClickEvents() {
        binding.btnFollow.setOnClickListener {
            viewModel.makeRequestFollow(targetEmail = args.userEmail)
        }
    }

    private fun initUserPostsRecyclerView() {
        binding.rvUserPostList.apply {
            adapter = postSmallListAdapter
            addItemDecoration(ProfilePostsGridItemDecoration(requireContext()))
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbUserToolbar.setupWithNavController(findNavController())
    }
}