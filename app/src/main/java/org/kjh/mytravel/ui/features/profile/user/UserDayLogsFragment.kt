package org.kjh.mytravel.ui.features.profile.user

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.FragmentUserDayLogsBinding
import org.kjh.mytravel.model.DayLog
import org.kjh.mytravel.ui.base.BaseFragment
import org.kjh.mytravel.ui.features.profile.*
import org.kjh.mytravel.ui.features.profile.my.MyProfileViewModel
import org.kjh.mytravel.utils.KakaoLinkUtils

/**
 * MyTravel
 * Class: UserDayLogsFragment
 * Created by jonghyukkang on 2022/05/30.
 *
 * Description:
 */
class UserDayLogsFragment
    : BaseFragment<FragmentUserDayLogsBinding>(R.layout.fragment_user_day_logs) {

    private var viewType: ViewType = ViewType.Grid
    private val myProfileViewModel: MyProfileViewModel by activityViewModels()
    private val viewModel: UserViewModel by viewModels({ requireParentFragment() })
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

    private fun onClickMore(dayLogItem: DayLog) {
        DayLogMoreBottomSheetDialog(
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
        binding.viewModel = viewModel

        binding.dayLogRecyclerView.apply {
            setHasFixedSize(true)
            itemAnimator = null
            adapter = dayLogMultipleViewAdapter

            if (viewType == ViewType.Grid) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                addItemDecoration(DayLogGridItemDecoration())
            } else {
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    override fun subscribeUi() {}

    companion object {
        private const val VIEW_TYPE = "VIEW_TYPE"

        @JvmStatic
        fun newInstance(viewType: Int) =
            UserDayLogsFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIEW_TYPE, viewType)
                }
            }
    }
}