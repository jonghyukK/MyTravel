package org.kjh.mytravel.ui.bookmark

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kjh.mytravel.MyProfileViewModel
import org.kjh.mytravel.NavGraphDirections
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentBookMarkBinding
import org.kjh.mytravel.model.Bookmark
import org.kjh.mytravel.ui.base.BaseFragment

@AndroidEntryPoint
class BookMarkFragment
    : BaseFragment<FragmentBookMarkBinding>(R.layout.fragment_book_mark) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()

    private val bookMarkListAdapter by lazy {
        BookmarkListAdapter(
            onClickItem     = { item -> onClickPostItem(item)},
            onClickBookmark = { item -> onClickBookmark(item)}
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
        binding.myProfileViewModel = myProfileViewModel

        initBookMarkRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                myProfileViewModel.isError.collectLatest {
                    it?.let { showError(it) }
                }
            }
        }
    }

    private fun initBookMarkRecyclerView() {
        binding.rvBookMarkList.apply {
            setHasFixedSize(true)
            adapter = bookMarkListAdapter
        }
    }

    private fun onClickPostItem(item: Bookmark) {
        navigateWithAction(NavGraphDirections.actionGlobalPlacePagerFragment(item.placeName))
    }

    private fun onClickBookmark(item: Bookmark) {
        myProfileViewModel.updateBookmark(item.postId, item.placeName)
    }
}