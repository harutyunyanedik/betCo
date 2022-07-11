package am.mil.domain.base

typealias HttpCatching<T> = HttpResponseHandler<Throwable, T>

sealed class HttpResponseHandler<out E, out S> {

    data class Error<out E>(val error: E) : HttpResponseHandler<E, Nothing>()
    data class Success<out T>(val value: T) : HttpResponseHandler<Nothing, T>()

    suspend inline fun onError(crossinline foo: suspend (E) -> Unit): HttpResponseHandler<E, S> {
        if (this is Error<E>)
            foo(error)
        return this
    }

    suspend inline fun onSuccess(crossinline foo: suspend (S) -> Unit): HttpResponseHandler<E, S> {
        if (this is Success<S>)
            foo(value)
        return this
    }

    // Collect result and error
    suspend inline fun fold(
        crossinline ifSuccess: suspend (S) -> Unit,
        crossinline ifError: suspend (E) -> Unit
    ) {
        when (this) {
            is Error<E> -> ifError(error)
            is Success<S> -> ifSuccess(value)
        }
    }

    companion object {
        suspend inline fun <T> catch(crossinline supplier: suspend () -> T): HttpCatching<T> =
            try {
                Success(supplier())
            } catch (t: Throwable) {
                Error(t)
            }
    }
}

suspend fun <T> (suspend () -> T).catching() = HttpResponseHandler.catch(this)