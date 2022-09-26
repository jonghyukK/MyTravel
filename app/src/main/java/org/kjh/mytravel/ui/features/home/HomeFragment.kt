package org.kjh.mytravel.ui.features.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentHomeBinding
import org.kjh.mytravel.model.LatestPostItemUiState
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.home.banner.BannerHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingDataAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestPostPagingLoadStateAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.profile.PostMoreBottomSheetDialog

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    private val latestPostListAdapter by lazy {
        LatestPostPagingDataAdapter(onClickMenu = ::onClickMenuOnLatestDayLog)
    }

    private val homeConcatAdapter by lazy {
        ConcatAdapter(
            BannerHorizontalWrapAdapter(),
            PlaceRankingHorizontalWrapAdapter(),
            latestPostListAdapter.withLoadStateFooter(
                footer = LatestPostPagingLoadStateAdapter(latestPostListAdapter::retry)
            )
        )
    }

    override fun onCreateView(
        inflater          : LayoutInflater,
        container         : ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor = Color.TRANSPARENT
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        binding.viewModel = viewModel
        binding.fragment  = this

        binding.homeConcatRecyclerView.apply {
            itemAnimator = null
            adapter = homeConcatAdapter
        }
    }

    override fun subscribeUi() {
        bindLatestPostsPagingData()
        refreshLatestPostsPagingData()
    }

    private fun bindLatestPostsPagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.latestPostsUiState
                    .filterNotNull()
                    .collectLatest {
                        latestPostListAdapter.submitData(it)
                    }
            }
        }
    }

    private fun refreshLatestPostsPagingData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeUiState
                    .map { it.refreshLatestPosts }
                    .distinctUntilChanged()
                    .collect {
                        if (it) {
                            latestPostListAdapter.refresh()
                            binding.homeConcatRecyclerView.scrollToPosition(0)
                            viewModel.refreshLatestPosts(false)
                        }
                    }
            }
        }
    }

    private fun onClickMenuOnLatestDayLog(dayLogItem: LatestPostItemUiState) {
        PostMoreBottomSheetDialog(
            isMyPost     = dayLogItem.isMyPost,
            deleteAction = dayLogItem.onDeleteDayLog
        ).apply {
            isFullScreen = false
        }.show(childFragmentManager, PostMoreBottomSheetDialog.TAG)
    }
}
