package am.mil.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCaseWithParams<P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P? = null): HttpCatching<R> =
        withContext(coroutineDispatcher) {
            HttpResponseHandler.catch { execute(parameters) }
        }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P?): R
}