package am.mil.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class FlowUseCase<P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<HttpCatching<R>> {
        return execute(parameters)
            .map { HttpResponseHandler.Success(it) }
            .catch { HttpResponseHandler.Error(it) }
            .flowOn(coroutineDispatcher)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): Flow<R>
}