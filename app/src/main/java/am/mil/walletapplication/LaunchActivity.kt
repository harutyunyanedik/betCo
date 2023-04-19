package am.mil.walletapplication

import am.mil.walletapplication.base.activity.BaseWalletActivity
import am.mil.walletapplication.base.utils.Utils
import am.mil.walletapplication.databinding.ActivityLaunchBinding
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil

class LaunchActivity : BaseWalletActivity() {

    private var binding: ActivityLaunchBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusBarColor(
            activity = this,
            color = ContextCompat.getColor(this, R.color.status_bar_color)
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
        }, SPLASH_DELAY)
    }

    companion object {
        private const val SPLASH_DELAY: Long = 2000
    }
}