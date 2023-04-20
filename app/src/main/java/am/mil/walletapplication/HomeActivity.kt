package am.mil.walletapplication

import am.mil.walletapplication.base.activity.BaseWalletActivity
import am.mil.walletapplication.base.fragment.BaseWalletFragment
import am.mil.walletapplication.base.utils.Utils
import am.mil.walletapplication.base.utils.setupWithNavController
import am.mil.walletapplication.databinding.ActivityHomeBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseWalletActivity() {

    private var binding: ActivityHomeBinding? = null
    private var currentNavController: LiveData<NavController>? = null
    val homeViewModel by viewModel<HomeViewModel>()
    private val navigationDestinationHandler = Handler(Looper.getMainLooper())
    private val navigationDestinationRunnable = Runnable {
        currentNavController?.value?.currentDestination?.id?.let {
            findPrimaryNavigationFragment()?.onStateVisible()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Utils.changeStatusBarColor(
            activity = this, color = ContextCompat.getColor(this, R.color.status_bar_color)
        )
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        noConnectionTextView = findViewById(R.id.noConnectionTextView)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        setupViews()
        observeLiveData()
    }

    private fun setupViews() {
        binding?.bottomNavigationView?.menu?.children?.forEachIndexed { index, menuItem ->
            menuItem.title = resources.getStringArray(R.array.navigationMenu_titles_ResIds_Array)[index]
        }
    }

    private fun observeLiveData() {

    }

    private fun setupBottomNavigationBar() {
        binding?.bottomNavigationView?.itemIconTintList = null

        val navGraphIds = listOf(
            R.navigation.nav_graph_home, R.navigation.nav_graph_history, R.navigation.nav_graph_scanner, R.navigation.nav_graph_cards, R.navigation.nav_graph_account
        )

        val controller = binding?.bottomNavigationView?.setupWithNavController(
            navGraphIds = navGraphIds, fragmentManager = supportFragmentManager, containerId = R.id.nav_host_container, intent = intent
        )

        currentNavController = controller

        controller?.observe(this) { navController ->
            when (navController.graph.startDestinationId) {
                R.id.homeFakeFragment -> setConditionalStartDestination(
                    navController, R.id.homeFragment
                )
                R.id.historyFakeFragment -> setConditionalStartDestination(
                    navController, R.id.historyFragment
                )
                R.id.scannerFakeFragment -> setConditionalStartDestination(
                    navController, R.id.scannerFragment
                )
                R.id.cardsFakeFragment -> setConditionalStartDestination(
                    navController, R.id.cardsFragment
                )
                R.id.accountFakeFragment -> setConditionalStartDestination(
                    navController, R.id.accountFragment
                )
            }

            navController.addOnDestinationChangedListener { _, _, _ ->
                findPrimaryNavigationFragment()?.onStateInVisible()
                navigationDestinationHandler.removeCallbacks(navigationDestinationRunnable)
                navigationDestinationHandler.postDelayed(
                    navigationDestinationRunnable, NAVIGATION_DESTINATION_DURATION
                )
            }
        }
    }

    private fun setConditionalStartDestination(
        navController: NavController, @IdRes startDestId: Int, bundle: Bundle? = null
    ) {
        navController.navInflater.inflate(R.navigation.nav_graph_home_general).apply {
            setStartDestination(startDestId)
            navController.setGraph(this, bundle)
        }
    }

    private fun findPrimaryNavigationFragment() = supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.getOrNull(
        0
    ) as? BaseWalletFragment

    @Deprecated("deprecated")
    override fun onBackPressed() {
        if (currentNavController?.value?.currentDestination?.id == R.id.homeFragment) {
            moveTaskToBack(true)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        private const val NAVIGATION_DESTINATION_DURATION: Long = 500
    }
}