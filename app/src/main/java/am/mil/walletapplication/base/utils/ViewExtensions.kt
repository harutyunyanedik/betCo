package am.mil.walletapplication.base.utils

import am.mil.walletapplication.HomeActivity
import am.mil.walletapplication.HomeViewModel
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.tabs.TabLayout
import com.journeyapps.barcodescanner.ScanOptions
import java.util.Calendar

fun Context.copyTextToClipboard(copyText: String) {
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText(WalletConstants.CLIPBOARD_SIMPLE_TEXT_LABEL, copyText)
    clipboard.setPrimaryClip(clip)
}

fun NavController.clearBackStack() = popBackStack(graph.startDestinationId, false)

val Fragment.intent: Intent
    get() = requireActivity().intent

val Fragment.homeActivity: HomeActivity?
    get() = activity as? HomeActivity

val Fragment.homeViewModel: HomeViewModel?
    get() = homeActivity?.homeViewModel

fun Fragment.requireHomeActivity(): HomeActivity {
    return requireActivity() as HomeActivity
}

fun Spinner.onItemSelected(
    onNothingSelected: (AdapterView<*>?) -> Unit = {},
    onItemSelected: (AdapterView<*>?, View?, Int, Long) -> Unit,
) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            onNothingSelected(p0)
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            onItemSelected(p0, p1, p2, p3)
        }
    }
}

fun Spinner.selectedPosition(): Int = if (this.selectedItemPosition == -1) 0 else this.selectedItemPosition

fun RadioGroup.checkedRadioButton(): RadioButton? = findViewById(checkedRadioButtonId)

fun RadioGroup.setIsEnable(isEnable: Boolean) = with(this) {
    for (i in 0 until this.childCount) {
        this.getChildAt(i).isEnabled = isEnable
    }
}

fun DatePicker.setup(calendar: Calendar) {
    this.init(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH),
        null
    )
}

fun DatePicker.setMaxDate(calendar: Calendar) {
    this.maxDate = calendar.timeInMillis
}

fun TabLayout.setMargins(left: Int, top: Int, right: Int, bottom: Int) {
    for (i in 0 until this.tabCount) {
        val tab = (this.getChildAt(0) as? ViewGroup)?.getChildAt(i)
        val marginLayoutParams = tab?.layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.setMargins(left, top, right, bottom)
    }
}

fun TabLayout.onTabSelected(body: (tab: TabLayout.Tab?) -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabSelected(tab: TabLayout.Tab?) {
            body.invoke(tab)
        }
    })
}

fun EditText.setSelectionText(inputText: String) = with(this) {
    this.setText(inputText)
    this.setSelection(inputText.length)
}

fun EditText.requestDirectionFocus(direction: Int) {
    this.requestFocus()
    this.setSelection(direction)
}

fun EditText.addTextWatcher(
    onBeforeTextChanged: (charSequence: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    onTextChanged: (charSequence: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> },
    onAfterTextChanged: (editable: Editable?) -> Unit,
) = this.addTextChangedListener(object : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        onBeforeTextChanged(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        onAfterTextChanged(s)
    }
})

fun ViewGroup.saveChildViewStates(): SparseArray<Parcelable> {
    val childViewStates = SparseArray<Parcelable>()
    children.forEach { child -> child.saveHierarchyState(childViewStates) }
    return childViewStates
}

fun ViewGroup.restoreChildViewStates(childViewStates: SparseArray<Parcelable>) {
    children.forEach { child -> child.restoreHierarchyState(childViewStates) }
}

fun Fragment.openQRScanner(title: String, launcher: ActivityResultLauncher<ScanOptions>) {
    ScanOptions().apply {
        setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
        setPrompt(title)
        setCameraId(0)
        setBeepEnabled(false)
        setOrientationLocked(false)
        setBarcodeImageEnabled(true)
    }.also {
        launcher.launch(it)
    }
}

fun Fragment.isPermissionGranted(permission: String) = ContextCompat.checkSelfPermission(
    requireContext(),
    permission
) == PermissionChecker.PERMISSION_GRANTED
