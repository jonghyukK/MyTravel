package org.kjh.mytravel.ui.features.profile

import org.kjh.mytravel.R
import org.kjh.mytravel.databinding.BsDialogDayLogMoreBinding
import org.kjh.mytravel.ui.base.BaseBottomSheetDialogFragment
import org.kjh.mytravel.ui.common.setOnThrottleClickListener

/**
 * MyTravel
 * Class: DayLogMoreBottomSheetDialog
 * Created by jonghyukkang on 2022/08/16.
 *
 * Description:
 */

class DayLogMoreBottomSheetDialog(
    private val isMyDayLog : Boolean = false,
    private val deleteAction: () -> Unit = {},
    private val kakaoShareAction: () -> Unit
) : BaseBottomSheetDialogFragment<BsDialogDayLogMoreBinding>(R.layout.bs_dialog_day_log_more) {

    override fun initView() {
        binding.isMyDayLog = isMyDayLog

        binding.btnDelete.setOnThrottleClickListener {
            deleteAction()
            dismiss()
        }

        binding.btnShare.setOnThrottleClickListener {
            kakaoShareAction()
            dismiss()
        }
    }

    override fun subscribeUi() {}

    companion object {
        const val TAG = "DayLogMoreBottomSheetDialog"
    }
}
