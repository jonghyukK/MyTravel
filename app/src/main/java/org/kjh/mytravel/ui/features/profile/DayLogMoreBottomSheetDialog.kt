package org.kjh.mytravel.ui.features.profile

import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsDialogDayLogMoreBinding
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment
import org.kjh.mytravel.utils.KakaoLinkUtils
import org.kjh.mytravel.utils.onThrottleClick

/**
 * MyTravel
 * Class: DayLogMoreBottomSheetDialog
 * Created by jonghyukkang on 2022/08/16.
 *
 * Description:
 */

class DayLogMoreBottomSheetDialog(
    private val isMyDayLog  : Boolean,
    private val deleteAction: () -> Unit,
    private val kakaoShareAction: () -> Unit
) : BaseBottomSheetDialogFragment<BsDialogDayLogMoreBinding>(R.layout.bs_dialog_day_log_more) {

    override fun initView() {
        binding.isMyDayLog = isMyDayLog

        binding.btnDelete.onThrottleClick {
            deleteAction()
            dismiss()
        }

        binding.btnShare.onThrottleClick {
            kakaoShareAction()
            dismiss()
        }
    }

    override fun subscribeUi() {}

    companion object {
        const val TAG = "DayLogMoreBottomSheetDialog"
    }
}
