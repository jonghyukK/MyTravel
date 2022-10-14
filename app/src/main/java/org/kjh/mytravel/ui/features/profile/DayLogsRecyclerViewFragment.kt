package org.kjh.mytravel.ui.features.profile

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentDayLogsRecyclerBinding
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import org.kjh.mytravel.ui.features.profile.user.UserViewModel
import org.kjh.mytravel.utils.KakaoLinkUtils

/**
 * MyTravel
 * Class: DayLogsRecyclerView
 * Created by jonghyukkang on 2022/10/14.
 *
 * Description:
 */
class DayLogsRecyclerViewFragment
    : BaseFragment<FragmentDayLogsRecyclerBinding>(R.layout.fragment_day_logs_recycler) {

    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val userViewModel: UserViewModel by viewModels({ requireParentFragment() })
    private val dayLogMultipleViewAdapter by lazy {
        DayLogMultipleViewAdapter(
            viewType        = viewType,
            onClickBookmark = ::requestUpdateBookmark,
            onClickMore     = ::onClickMore
        )
    }
    private var viewType: ViewType = ViewType.Grid
    private var isMine  : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewType =
                if (it.getInt(VIEW_TYPE) == DAY_LOGS_GRID_PAGE_INDEX) {
                    ViewType.Grid
                } else {
                    ViewType.Linear
                }

            isMine = it.getBoolean(IS_MINE)
        }
    }

    override fun initView() {
        binding.isMine = isMine

        binding.dayLogCommonRecyclerView.apply {
            adapter = dayLogMultipleViewAdapter

            if (viewType is ViewType.Grid) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                addItemDecoration(DayLogGridItemDecoration())
            } else {
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun subscribeUi() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (isMine) {
                    myProfileViewModel.myProfileUiState.collect { user ->
                        dayLogMultipleViewAdapter.submitList(user?.dayLogs ?: emptyList())
                        binding.isEmpty = user?.dayLogs.isNullOrEmpty()
                    }
                } else {
                    userViewModel.uiState.collect { user ->
                        dayLogMultipleViewAdapter.submitList(user.userItem?.dayLogs ?: emptyList())
                        binding.isEmpty = user.userItem?.dayLogs.isNullOrEmpty()
                    }
                }
            }
        }
    }

    private fun requestUpdateBookmark(dayLog: DayLog) {
        myProfileViewModel.updateBookmark(dayLog.placeName)
    }

    private fun onClickMore(dayLogItem: DayLog) {
        DayLogMoreBottomSheetDialog(
            isMyDayLog = isMine,
            deleteAction = { myProfileViewModel.deleteDayLog(dayLogItem.dayLogId) },
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

    companion object {
        private const val VIEW_TYPE = "VIEW_TYPE"
        private const val IS_MINE = "IS_MINE"

        @JvmStatic
        fun newInstance(viewType: Int, isMine: Boolean) =
            DayLogsRecyclerViewFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIEW_TYPE, viewType)
                    putBoolean(IS_MINE, isMine)
                }
            }
    }
}