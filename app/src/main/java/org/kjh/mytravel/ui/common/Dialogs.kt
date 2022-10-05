package org.kjh.mytravel.ui.common

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.kjh.mytravel.R

/**
 * MyTravel
 * Class: Dialogs
 * Created by jonghyukkang on 2022/08/02.
 *
 * Description:
 */
object Dialogs {

    fun showDefaultDialog(
        ctx: Context,
        title: String,
        msg  : String,
        cancelable: Boolean = false,
        negBtnText: String = ctx.getString(R.string.cancel),
        posBtnText: String = ctx.getString(R.string.confirm),
        negAction: () -> Unit = {},
        posAction: () -> Unit = {}
    ) {
        MaterialAlertDialogBuilder(ctx)
            .setTitle(title)
            .setMessage(msg)
            .setCancelable(cancelable)
            .setNegativeButton(negBtnText) { _, _ -> negAction() }
            .setPositiveButton(posBtnText) { _, _ -> posAction() }
            .show()
    }
}