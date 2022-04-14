package org.kjh.mytravel.ui.user

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
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.MyProfileViewModel
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.ProfilePostsGridItemDecoration
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUserBinding
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.PostSmallListAdapter
import org.kjh.mytravel.ui.base.BaseFragment
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

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val postSmallListAdapter by lazy {
        PostSmallListAdapter(
            onClickPost     = { item -> onClickPostItem(item)},
            onClickBookmark = { item -> onClickBookmark(item)}
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.myProfileViewModel = myProfileViewModel
        binding.fragment = this

        initToolbarWithNavigation()
        initUserPostsRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myProfileViewModel.isError.collectLatest {
                    Logger.e("User")
                    it?.let {
                        showError(it)
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->

                    uiState.isError?.let {
                        showError(it)
                        viewModel.shownErrorToast()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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

    private fun onClickPostItem(item: Post) {
        navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
    }

    private fun onClickBookmark(item: Post) {
        myProfileViewModel.updateBookmark(item.postId, item.placeName)
    }

    fun onClickFollowOrUnFollow(v: View) {
        viewModel.makeRequestFollow(targetEmail = args.userEmail)
    }

    private fun initUserPostsRecyclerView() {
        binding.rvUserPostList.apply {
            adapter = postSmallListAdapter
            setHasFixedSize(true)
            addItemDecoration(ProfilePostsGridItemDecoration(requireContext()))
        }
    }

    private fun initToolbarWithNavigation() {
        binding.tbUserToolbar.setupWithNavController(findNavController())
    }
}