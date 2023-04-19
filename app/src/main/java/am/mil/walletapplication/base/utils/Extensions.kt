package am.mil.walletapplication.base.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("unused")
fun <T> AppCompatActivity.viewLifecycle(onDestroyView: (() -> Unit)? = null): ReadWriteProperty<Activity, T> =
    object : ReadWriteProperty<Activity, T>, LifecycleObserver {

        private var value: T? = null

        init {
            this@viewLifecycle.lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            onDestroyView?.invoke()
            value = null
        }

        override fun getValue(thisRef: Activity, property: KProperty<*>): T {
            return value ?: error("Called before onCreate or after onDestroy.")
        }

        override fun setValue(thisRef: Activity, property: KProperty<*>, value: T) {
            this.value = value
        }

    }


/**
 * @author https://gist.github.com/frel/5f3f928c27f4106ffd420a3d99c8037c
 *
 * An extension to bind and unbind a value based on the view lifecycle of a Fragment.
 * The binding will be unbound in onDestroyView.
 *
 * @throws IllegalStateException If the getter is invoked before the binding is set,
 *                               or after onDestroyView an exception is thrown.
 * @sample 'private var binding: TheViewBinding by viewLifecycle()'
 */
fun <T> Fragment.viewLifecycle(onDestroyView: (() -> Unit)? = null): ReadWriteProperty<Fragment, T> =
    object : ReadWriteProperty<Fragment, T>, LifecycleObserver {

        private var binding: T? = null

        init {
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycle) { owner: LifecycleOwner? ->
                    owner?.lifecycle?.addObserver(this)
                }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            onDestroyView?.invoke()
            binding = null
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            return binding ?: error("Called before onCreateView or after onDestroyView.")
        }

        override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
            this.binding = value
        }

    }

fun View.unbindDrawables() {
    try {
        if (background != null) {
            background.callback = null
        }
        if (this is ImageView) {
            setImageBitmap(null)
        } else if (this is ViewGroup) {
            for (i in 0 until childCount) {
                getChildAt(i).unbindDrawables()
                if (i > 100) {
                    break
                }
            }
            if (this !is AdapterView<*>) {
                removeAllViews()
            }
        }
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun <T : View> ViewGroup.getViewsByType(tClass: Class<T>): List<T> {
    return mutableListOf<T?>().apply {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            (child as? ViewGroup)?.let {
                addAll(child.getViewsByType(tClass))
            }
            if (tClass.isInstance(child))
                add(tClass.cast(child))
        }
    }.filterNotNull()
}

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }

fun Activity.clearFocus() {
    window?.decorView?.findViewById<View?>(android.R.id.content)?.clearFocus()
}

/**
 * Changes size of dialog to adjust it when keyboard is open
 */
fun Dialog.setPeekHeight() {
    val bottomSheet =
        this.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
    val behavior = BottomSheetBehavior.from(bottomSheet!!)

    val displayMetrics = this.ownerActivity!!.resources.displayMetrics
    val height = displayMetrics.heightPixels
    val maxHeight = (height) //(height * 0.88).toInt()
    behavior.peekHeight = maxHeight
}

fun Context.makePhoneCall(number: String): Boolean {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

fun AppCompatActivity.requestAllPermissions(
    permissionsArray: Array<String>,
    requestCode: Int
) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}

fun <T> MutableLiveData<T>.setValueIfNew(newValue: T) {
    if (this.value != newValue) value = newValue
}

@Suppress("UNCHECKED_CAST")
fun <T> T.clone(): T {
    val byteArrayOutputStream = ByteArrayOutputStream()
    ObjectOutputStream(byteArrayOutputStream).use { outputStream ->
        outputStream.writeObject(this)
    }

    val bytes = byteArrayOutputStream.toByteArray()

    ObjectInputStream(ByteArrayInputStream(bytes)).use { inputStream ->
        return inputStream.readObject() as T
    }
}

@Suppress("unused")
fun TextView.setClickableText(
    string: String?,
    startIndex: Int = 0,
    endIndex: Int = -1,
    @ColorRes colorRes: Int? = null,
    isPreventDoubleClick: Boolean = false,
    onTextClick: (() -> Unit)? = null
) {
    val spannableString = SpannableString(string)

    val newEndIndex = if (endIndex == -1) spannableString.length else endIndex

    colorRes?.let {
        val color = ResourcesCompat.getColor(context.resources, colorRes, context.theme)
        spannableString.setSpan(
            ForegroundColorSpan(color),
            startIndex,
            newEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    onTextClick?.let {
        var lastTimeClicked: Long = 0
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                if (isPreventDoubleClick) {
                    if (SystemClock.elapsedRealtime() - lastTimeClicked < 1500L) {
                        return
                    }
                    lastTimeClicked = SystemClock.elapsedRealtime()
                    onTextClick()
                } else
                    onTextClick()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        }
        spannableString.setSpan(
            clickableSpan,
            startIndex,
            newEndIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        movementMethod = LinkMovementMethod.getInstance()
    }
    text = spannableString
}

fun AppCompatActivity.getActivityFragments(): List<Fragment> {
    val fragments: MutableList<Fragment> = arrayListOf()
    getFragments(supportFragmentManager, fragments)
    return fragments
}

fun getFragments(fragmentManager: FragmentManager, fragments: MutableList<Fragment>) {
    val allFragments = fragmentManager.fragments
    if (allFragments.isEmpty()) {
        return
    }

    for (fragment in allFragments) {
        fragments.add(fragment)
        getFragments(fragment.childFragmentManager, fragments)
    }
}

@Suppress("unused")
fun ViewGroup.isAllEnabled(isEnabled: Boolean) {
    forEach { child ->
        child.isEnabled = isEnabled
        if (child is ViewGroup) {
            child.isAllEnabled(isEnabled)
        }
    }
}

@Suppress("unused")
fun ViewPager2.doOnPageSelected(onPageSelected: (position: Int) -> Unit) =
    registerOnPageChangeCallback(onPageSelected = onPageSelected)

inline fun ViewPager2.registerOnPageChangeCallback(
    crossinline onPageScrolled: (
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int,
    ) -> Unit = { _, _, _ -> },
    crossinline onPageSelected: (position: Int) -> Unit = {},
    crossinline onPageScrollStateChanged: (state: Int) -> Unit = {},
): ViewPager2.OnPageChangeCallback {

    val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) =
            onPageScrolled.invoke(position, positionOffset, positionOffsetPixels)

        override fun onPageSelected(position: Int) = onPageSelected.invoke(position)

        override fun onPageScrollStateChanged(state: Int) = onPageScrollStateChanged.invoke(state)
    }
    registerOnPageChangeCallback(onPageChangeCallback)

    return onPageChangeCallback
}