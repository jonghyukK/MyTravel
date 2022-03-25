package org.kjh.mytravel.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentBookMarkBinding
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.ui.MainActivity
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.profile.ProfileViewModel

@AndroidEntryPoint
class BookMarkFragment : BaseFragment<FragmentBookMarkBinding>(R.layout.fragment_book_mark) {

    private val profileViewModel: ProfileViewModel by activityViewModels()

    private val bookMarkListAdapter by lazy {
        BookmarkListAdapter({ item -> onClickPostItem(item)}) { item ->
            profileViewModel.updateMyBookmark(item.postId)
        }
    }

    private fun onClickPostItem(item: Bookmark) {
        val action = NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName)
        findNavController().navigate(action)
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

        initBookMarkRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.uiState.collect { uiState ->
                    uiState.userItem?.let {
                        binding.groupBookmarkEmpty.isVisible = it.bookMarks.isEmpty()

                        bookMarkListAdapter.submitList(it.bookMarks)
                    }
                }
            }
        }
    }

    private fun initBookMarkRecyclerView() {
        binding.rvBookMarkList.apply {
            adapter = bookMarkListAdapter
        }
    }
}