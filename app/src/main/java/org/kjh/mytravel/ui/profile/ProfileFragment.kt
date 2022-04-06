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
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.ProfilePostsGridItemDecoration
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileBinding
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.model.Post
import org.kjh.mytravel.ui.PostSmallListAdapter
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.bookmark.BookMarkViewModel
import org.kjh.mytravel.ui.bookmark.BookmarkUiState


/**
 *
 *  [ UiState Modeling ]
 *
 *  해당 Fragment에선 아래와 같이 UiState를 Sealed Class로 묶기 보단,
 *  UiState를 Data Class로 만들어 부분적인 Update를 이용하는게 나음.
 *  Xml에 DataBinding하기에도 편함.
 *
 *  sealed class ProfileUiState {
        object Loading  : ProfileUiState()
        object NotLogin : ProfileUiState()
        data class Success(val profileItem: User): ProfileUiState()
        data class Error  (val error: Throwable?): ProfileUiState()
    }
 *
 */
@AndroidEntryPoint
class ProfileFragment
    :BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val bookmarkViewModel: BookMarkViewModel by activityViewModels()
    private val viewModel: ProfileViewModel by activityViewModels()

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
        binding.viewModel = viewModel
        binding.fragment = this

        initToolbarWithNavigation()
        initMyPostRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { loginState ->
                    when (loginState) {
                        is LoginState.LoggedIn -> handleLoggedInFlow()
                        is LoginState.NotLogIn -> handleNotLoginFlow()
                    }
                }
            }
        }
    }

    private fun handleLoggedInFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.profileItem?.let {
                        myPostListAdapter.submitList(uiState.profileItem.posts)
                    }

                    uiState.isError?.let {
                        showError(it)
                        viewModel.shownErrorToast()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookmarkViewModel.uiState.collectLatest { bookmarkState ->
                    viewModel.updatePostBookmarkState(bookmarkState.bookmarkItems)

                    bookmarkState.isError?.let {
                        showError(it)
                        bookmarkViewModel.shownErrorToast()
                    }
                }
            }
        }
    }

    private fun handleNotLoginFlow() {
        navigateWithAction(
            ProfileFragmentDirections.actionProfileFragmentToNotLoginFragment()
        )
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
            adapter = myPostListAdapter
            setHasFixedSize(true)
            addItemDecoration(ProfilePostsGridItemDecoration(requireContext()))
        }
    }

    private fun onClickPost(item: Post) {
        navigateWithAction(
            NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
    }

    private fun onClickBookmark(item: Post) {
        bookmarkViewModel.updateBookmark(item.postId, item.placeName)
    }

    fun onClickProfileEdit(v: View) {
        navigateWithAction(
            ProfileFragmentDirections.actionProfileFragmentToProfileEditFragment()
        )
    }
}