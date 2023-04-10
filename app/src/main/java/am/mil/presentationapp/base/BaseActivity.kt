package am.mil.presentationapp.base

import am.mil.presentationapp.WalletApplication.Companion.networkStateLiveData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import am.mil.presentationapp.R
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView

abstract class BaseActivity : AppCompatActivity() {

    var noConnectionTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        networkStateLiveData.observe(this) {
            showNoInternetView(!it)
        }
    }

    private fun showNoInternetView(display: Boolean) {
        if (display) {
            val enterAnim = AnimationUtils.loadAnimation(this, R.anim.anim_enter_from_bottom)
            noConnectionTextView?.startAnimation(enterAnim)
        } else {
            val exitAnim = AnimationUtils.loadAnimation(this, R.anim.anim_exit_to_bottom)
            noConnectionTextView?.startAnimation(exitAnim)
        }
        noConnectionTextView?.visibility = if (display) View.VISIBLE else View.GONE
    }
}