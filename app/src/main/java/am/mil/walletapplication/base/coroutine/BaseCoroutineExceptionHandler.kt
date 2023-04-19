package am.mil.walletapplication.base.coroutine

import am.mil.walletapplication.base.utils.WalletConstants.EMPTY_STRING
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

class BaseCoroutineExceptionHandler(override val key: CoroutineContext.Key<*>) :
    CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        try {
            Log.e("Global error", exception.localizedMessage ?: EMPTY_STRING)
            // remove loader
        } catch (ex: Exception) {
            Log.e("Coroutine Exception", ex.localizedMessage ?: EMPTY_STRING)
        }
    }
}