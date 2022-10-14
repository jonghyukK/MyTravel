package org.kjh.mytravel.ui.features.home

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
import org.kjh.mytravel.model.LatestDayLogItemUiState
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.model.common.UiState
import org.kjh.mytravel.ui.features.home.banner.BannerHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.home.banner.BannerListAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestDayLogPagingDataAdapter
import org.kjh.mytravel.ui.features.home.latest.LatestDayLogPagingLoadStateAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingHorizontalWrapAdapter
import org.kjh.mytravel.ui.features.home.ranking.PlaceRankingListAdapter
import org.kjh.mytravel.ui.features.profile.DayLogMoreBottomSheetDialog
import org.kjh.mytravel.utils.KakaoLinkUtils

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    val viewModel: HomeViewModel by activityViewModels()

    private val bannerListAdapter by lazy { BannerListAdapter() }
    private val rankingListAdapter by lazy { PlaceRankingListAdapter() }
    private val latestDayLogListAdapter by lazy {
        LatestDayLogPagingDataAdapter(onClickMenu = ::onClickMenuOnLatestDayLog)
    }
    private val homeConcatAdapter by lazy {
        ConcatAdapter(
            BannerHorizontalWrapAdapter(bannerListAdapter = bannerListAdapter),
            PlaceRankingHorizontalWrapAdapter(rankingListAdapter = rankingListAdapter),
            latestDayLogListAdapter.withLoadStateFooter(
                footer = LatestDayLogPagingLoadStateAdapter(latestDayLogListAdapter::retry)
            )
        )
    }

    override fun initView() {
        binding.viewModel = viewModel

        binding.homeConcatRecyclerView.apply {
            itemAnimator = null
            adapter = homeConcatAdapter
        }
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.bannersUiState.collect { bannerUiState ->
                        when (bannerUiState) {
                            is UiState.Init,
                            is UiState.Loading -> {}
                            is UiState.Success -> bannerListAdapter.submitList(bannerUiState.data)
                            is UiState.Error -> {
                                showToast(bannerUiState.errorMsg)
                                bannerUiState.errorAction?.invoke()
                            }
                        }
                    }
                }

                launch {
                    viewModel.rankingsUiState.collect { rankingUiState ->
                        when (rankingUiState) {
                            is UiState.Init,
                            is UiState.Loading -> {}
                            is UiState.Success -> rankingListAdapter.submitList(rankingUiState.data)
                            is UiState.Error -> {
                                showToast(rankingUiState.errorMsg)
                                rankingUiState.errorAction?.invoke()
                            }
                        }
                    }
                }

                launch {
                    viewModel.latestDayLogsUiState
                        .filterNotNull()
                        .collectLatest {
                            latestDayLogListAdapter.submitData(it)
                        }
                }

                launch {
                    viewModel.homeUiState
                        .map { it.refreshLatestDayLogs }
                        .distinctUntilChanged()
                        .collect { doRefresh ->
                            if (doRefresh) {
                                latestDayLogListAdapter.refresh()
                                binding.homeConcatRecyclerView.scrollToPosition(0)
                                viewModel.refreshLatestDayLogs(false)
                            }
                        }
                }
            }
        }
    }

    private fun onClickMenuOnLatestDayLog(dayLogItem: LatestDayLogItemUiState) {
        DayLogMoreBottomSheetDialog(
            isMyDayLog   = dayLogItem.isMyDayLog,
            deleteAction = dayLogItem.onDeleteDayLog,
            kakaoShareAction = { KakaoLinkUtils.sendDayLogKakaoLink(
                ctx = requireContext(),
                placeName = dayLogItem.placeName,
                content  = dayLogItem.content ?: "",
                imageUrl = dayLogItem.imageUrl[0]
            )}
        ).apply {
            isFullScreen = false
        }.show(childFragmentManager, DayLogMoreBottomSheetDialog.TAG)
    }
}
