package am.mil.walletapplication.base.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveEventData<T> : MutableLiveData<T>() {

    private val isPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) {
            if (isPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        }
    }

    override fun setValue(value: T?) {
        isPending.set(true)
        super.setValue(value)
    }

    override fun postValue(value: T?) {
        isPending.set(true)
        super.postValue(value)
    }

    @Suppress("unused")
    fun clearValue() {
        value = null
    }
}
