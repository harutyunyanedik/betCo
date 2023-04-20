package am.mil.walletapplication.base.utils

import am.mil.walletapplication.R
import am.mil.walletapplication.base.utils.Helper.getDeviceDisplayMetrics
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.createDialog(layoutSource: Int, isCancelable: Boolean = true): Dialog? {
    return createDialog(layoutSource, isCancelable, requireContext())
}

fun Activity.createDialog(layoutSource: Int, isCancelable: Boolean = true): Dialog? {
    return createDialog(layoutSource, isCancelable, this)
}

fun Activity.createDialog(view: View, isCancelable: Boolean = true): Dialog? {
    return createDialog(view, isCancelable, this)
}

fun Activity.createWalletMessageDialog(
    errorMessageData: WalletDefaultDialogData, isShowCancelButton: Boolean = false
): Dialog? {
    return createWalletMessageDialog(errorMessageData, this, isShowCancelButton)
}

fun createDialog(layoutSource: Int, isCancelable: Boolean, context: Context): Dialog? {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(layoutSource)
    dialog.setCanceledOnTouchOutside(isCancelable)
    dialog.setCancelable(isCancelable)
    try {
        dialog.window?.attributes = getLayoutParams(dialog, context)
        dialog.show()
    } catch (e: Exception) {
        return null
    }
    return dialog
}

fun createDialog(view: View, isCancelable: Boolean, context: Context): Dialog? {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setContentView(view)
    dialog.setCanceledOnTouchOutside(isCancelable)
    dialog.setCancelable(isCancelable)
    try {
        dialog.window?.attributes = getLayoutParams(dialog, context)
        dialog.show()
    } catch (e: Exception) {
        return null
    }
    return dialog
}

fun createWalletMessageDialog(
    messageData: WalletDefaultDialogData, context: Context, isShowCancelButton: Boolean = false
): Dialog {
    val materialAlertDialog = MaterialAlertDialogBuilder(context, R.style.BaseMaterialAlertDialogStyle).apply {
        setTitle(messageData.title)
        setMessage(messageData.message)
        setCancelable(messageData.isCancelable)
        messageData.iconRes?.let { setIcon(it) }
    }

    materialAlertDialog.setPositiveButton(messageData.positiveButtonText) { dialogInterface, _ ->
        messageData.okClick()
        dialogInterface.dismiss()
    }
    if (isShowCancelButton) {
        materialAlertDialog.setNegativeButton(messageData.negativeButtonText) { dialogInterface, _ ->
            messageData.cancelClick()
            dialogInterface.dismiss()
        }
    }
    val dialog = materialAlertDialog.create()
    dialog.show()

    return dialog
}

fun getLayoutParams(dialog: Dialog, context: Context): WindowManager.LayoutParams {
    val displayMetrics = getDeviceDisplayMetrics(context as Activity)
    val dialogWidth = if (displayMetrics.widthPixels <= displayMetrics.heightPixels) displayMetrics.widthPixels else displayMetrics.heightPixels
    val layoutParams = WindowManager.LayoutParams()
    layoutParams.copyFrom(dialog.window?.attributes)
    layoutParams.width = dialogWidth - context.resources.getDimensionPixelSize(R.dimen.dialog_margin)
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    return layoutParams
}

data class WalletDefaultDialogData(
    var title: String = WalletConstants.EMPTY_STRING, var message: String = WalletConstants.EMPTY_STRING, @DrawableRes var iconRes: Int? = null, var isCancelable: Boolean = true, var positiveButtonText: String = WalletConstants.EMPTY_STRING, var negativeButtonText: String = WalletConstants.EMPTY_STRING, var okClick: () -> Unit = {}, var cancelClick: () -> Unit = {}
)
