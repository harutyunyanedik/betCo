package am.mil.walletapplication.base.utils

import am.mil.walletapplication.R
import am.mil.walletapplication.base.utils.WalletConstants.EMPTY_STRING
import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast

object Helper {

    fun getDeviceDisplayMetrics(context: Activity): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    fun getStatusBarHeight(context: Activity): Int {
        val height: Int
        val idStatusBarHeight = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        height = when {
            idStatusBarHeight > 0 -> context.resources.getDimensionPixelSize(idStatusBarHeight)
            else -> 0
        }
        return height
    }

    fun hideSoftKeyboard(view: View?) {
        if (view != null) {
            view.clearFocus()
            val inputMethodManager = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            unwrap(view.context)?.clearFocus()
        }
    }

    fun showSoftKeyboard(view: View?) {
        view?.postDelayed({
            view.requestFocus()
            val inputMethodManager = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            if (view is EditText) view.setSelection(view.text?.length ?: 0)
        }, 300)
    }

    fun unwrap(ctx: Context): Activity? {
        var context: Context? = ctx
        while (context !is Activity && context is ContextWrapper) {
            context = context.baseContext
        }
        return context as? Activity
    }

    fun makeTransparentStatusBar(isTransparent: Boolean, activity: Activity) {
        if (isTransparent) activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        else activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    fun copyToClipboard(context: Context, label: String?, text: String?, toastMessageRes: Int = R.string.wallet_global_text_copied) {
        val manager: ClipboardManager? = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clipData = ClipData.newPlainText(label, text)
        manager?.setPrimaryClip(clipData)
        showToast(context, toastMessageRes)
    }

    fun showToast(context: Context, resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, context.resources.getString(resId), duration).show()
    }


    fun getDisplayDensityFolderName(context: Context?): String {
        context ?: return "xhdpi"
        val density = context.resources.displayMetrics.density

        if (density >= 4.0) {
            return "xxxhdpi"
        }
        if (density >= 3.0) {
            return "xxhdpi"
        }
        if (density >= 2.0) {
            return "xhdpi"
        }
        if (density >= 1.5) {
            return "hdpi"
        }

        return "mdpi"
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo?.isConnected!!
    }

    fun openPhoneDialApp(context: Context?, phoneNumber: String?) {
        if (phoneNumber.isNullOrBlank()) return
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context?.startActivity(intent)
    }

    fun openEmailApp(context: Context?, email: String?, subject: String? = EMPTY_STRING, body: String? = EMPTY_STRING) {
        if (email.isNullOrBlank()) return
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        emailIntent.type = ClipDescription.MIMETYPE_TEXT_PLAIN
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        context?.startActivity(Intent.createChooser(emailIntent, "Send mail using..."))
    }

//    fun parseToDateTime(str: String?): DateTime? { // todo implement joda time lib
//        val indexOfPlus = str?.indexOf("+") ?: return null
//        return if (indexOfPlus == -1) {
//            DateTime.parse(str)
//        } else {
//            DateTime.parse(str.substring(0, indexOfPlus))
//        }
//    }
}