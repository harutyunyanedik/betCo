package am.mil.domain.base

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

abstract class CallbackUseCase<P, R>(private val coroutineDispatcher: CoroutineDispatcher) : KoinComponent {

    private val context: Context by inject()

    suspend operator fun invoke(
        parameters: P? = null,
        resultCallBackBetCo: BetCoApiResultCallback<R?>,
        isShowLoader: Boolean = true
    ) {
        if (!ConnectionHelper.isNetworkConnected(context)) {
            // remove loader
            resultCallBackBetCo.onError("No connection Error")
            return
        }
        withContext(
            coroutineDispatcher + BaseBetCoCoroutineExceptionHandler(
                CoroutineExceptionHandler,
                resultCallBackBetCo
            )
        ) {
            if (isShowLoader) {
                // addLoader
            }
            val response = execute(parameters)
            val responseBody = response.body()

            GlobalScope.launch(
                Dispatchers.Main + BaseBetCoCoroutineExceptionHandler(
                    CoroutineExceptionHandler, resultCallBackBetCo
                )
            ) {
                if (isShowLoader) {
                    // remove loader
                }
                if (response.isSuccessful) {
                    resultCallBackBetCo.onSuccess(responseBody)
                } else {
                    //Remove loader again for sequence calls
                    if (isShowLoader) {
                        // remove loader
                    }
                    resultCallBackBetCo.onError(response.errorBody().toString())
                    resultCallBackBetCo.onNotHandledError(response.errorBody())
                }
            }
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P?): Response<R>
}

@Keep
interface BetCoApiResultCallback<T> {
    fun onSuccess(response: T)

    @Deprecated("Use onError(status: Status.Failure) instead.")
    fun onError() {
    }

    /**
     * @return true if handled
     */
    fun onError(status: Any): Boolean = false
    fun onNotHandledError(error: Any? = null) {}
}

class BaseBetCoCoroutineExceptionHandler(
    override val key: CoroutineContext.Key<*>,
    private val resultCallBackBetCo: BetCoApiResultCallback<*>? = null
) : CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        try {
            Log.e("Global error", exception.localizedMessage ?: "")
            // remove loader
            resultCallBackBetCo?.onError()
        } catch (ex: Exception) {
            Log.e("Coroutine Exception", ex.localizedMessage ?: "")
        }
    }
}
