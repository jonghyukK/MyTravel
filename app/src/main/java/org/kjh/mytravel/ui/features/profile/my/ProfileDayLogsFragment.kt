package org.kjh.mytravel.ui.features.profile.my

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentProfileDayLogsBinding
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.*
import org.kjh.mytravel.utils.KakaoLinkUtils


class ProfileDayLogsFragment
    : BaseFragment<FragmentProfileDayLogsBinding>(R.layout.fragment_profile_day_logs) {

    private var viewType: ViewType = ViewType.Grid
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val dayLogMultipleViewAdapter by lazy {
        DayLogMultipleViewAdapter(
            viewType        = viewType,
            onClickBookmark = ::requestUpdateBookmark,
            onClickMore     = ::onClickMore
        )
    }

    private fun requestUpdateBookmark(dayLog: DayLog) {
        myProfileViewModel.updateBookmark(dayLog.placeName)
    }

    private fun onClickMore(dayLog: DayLog) {
        val dayLogMoreBs = DayLogMoreBottomSheetDialog(
            isMyDayLog   = true,
            deleteAction = { myProfileViewModel.deleteDayLog(dayLog.dayLogId) },
            kakaoShareAction = { KakaoLinkUtils.sendDayLogKakaoLink(
                ctx = requireContext(),
                placeName = dayLog.placeName,
                content   = dayLog.content ?: "",
                imageUrl  = dayLog.imageUrl[0]
            )}
        ).apply {
            isFullScreen = false
        }

        dayLogMoreBs.show(childFragmentManager, DayLogMoreBottomSheetDialog.TAG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            viewType = if (it.getInt(VIEW_TYPE) == DAY_LOGS_GRID_PAGE_INDEX) {
                ViewType.Grid
            } else {
                ViewType.Linear
            }
        }
    }

    override fun initView() {
        binding.viewModel = myProfileViewModel

        binding.dayLogRecyclerView.apply {
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

    }

    companion object {
        private const val VIEW_TYPE = "VIEW_TYPE"

        @JvmStatic
        fun newInstance(viewType: Int) =
            ProfileDayLogsFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIEW_TYPE, viewType)
                }
            }
    }
}