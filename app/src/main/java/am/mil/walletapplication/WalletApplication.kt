package am.mil.walletapplication

import am.mil.walletapplication.base.coroutine.BaseCoroutineExceptionHandler
import am.mil.walletapplication.base.utils.Prefs
import am.mil.walletapplication.di.appComponent
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WalletApplication : Application(), LifecycleObserver {

    private lateinit var currentActivityState: Lifecycle

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupKoin()
        currentActivityState = ProcessLifecycleOwner.get().lifecycle
        currentActivityState.addObserver(this)
        listenToNetworkChange()
        Prefs.Builder().setContext(this).setPrefsName(packageName).setUseDefaultSharedPreference(true).build()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@WalletApplication)
            androidFileProperties()
            modules(appComponent)
        }
    }

    private fun listenToNetworkChange() {
        val networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isLastNetworkStateWasConnected = true
                networkStateLiveData.postValue(true)
            }

            override fun onLost(network: Network) {
                if (isLastNetworkStateWasConnected == true && (currentActivityState.currentState == Lifecycle.State.STARTED || currentActivityState.currentState == Lifecycle.State.RESUMED)) {
                    if (System.currentTimeMillis() - (lastNoInternetShownToastTime ?: 0) > SHOW_NO_INTERNET_CONNECTION_POPUP_TIME_RANGE) {
                        lastNoInternetShownToastTime = System.currentTimeMillis()
                    }
                }
                isLastNetworkStateWasConnected = false
                networkStateLiveData.postValue(false)
            }
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val request = NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }

    companion object {
        lateinit var instance: Application
        val networkStateLiveData: MutableLiveData<Boolean> = MutableLiveData()
        var isLastNetworkStateWasConnected: Boolean? = null
        var lastNoInternetShownToastTime: Long? = null
        const val SHOW_NO_INTERNET_CONNECTION_POPUP_TIME_RANGE = 4000

        fun getCoroutineContext() = Dispatchers.Main + SupervisorJob() + BaseCoroutineExceptionHandler(
            CoroutineExceptionHandler
        )
    }
}