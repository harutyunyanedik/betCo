package am.mil.presentationapp.base.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

object Utils {

    fun changeStatusBarColor(isTranslucentStatusBar: Boolean = false, color: Int = 0, activity: Activity) {
        if (isTranslucentStatusBar) {
            val window: Window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        } else {
            val window: Window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = color
        }
    }

    fun setLightStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = activity.window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            activity.window.decorView.systemUiVisibility = flags
        }
    }

    @SuppressLint("ResourceAsColor")
    fun createGradientDrawable(
        shape: Int = GradientDrawable.RECTANGLE,
        colorArgb: Int,
        cornerRadius: Float = 0f,
        gradientDrawableStroke: Boolean = false
    ): GradientDrawable {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = shape
        gradientDrawable.cornerRadius = cornerRadius
        if (gradientDrawableStroke) {
            gradientDrawable.setStroke(1, ColorStateList.valueOf(colorArgb))
        } else {
            gradientDrawable.color = ColorStateList.valueOf(colorArgb)
        }
        return gradientDrawable
    }

    fun isPackageInstalled(
        packageName: String,
        packageManager: PackageManager
    ): Boolean {
        var found = true
        try {
            packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            found = false
        }
        return found
    }
}
