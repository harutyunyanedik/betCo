package am.mil.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCaseWithoutParams<R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(): HttpCatching<R> = withContext(coroutineDispatcher) {
        ::execute.catching()
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R
}