package am.mil.walletapplication.base.viewmodel

import am.mil.walletapplication.WalletApplication
import am.mil.walletapplication.base.activity.BaseWalletActivity
import am.mil.walletapplication.base.fragment.BaseWalletFragment
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinComponent
import java.util.*

open class BaseObservableViewModel : ViewModel(), CoroutineScope, Observable, KoinComponent {

    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun onCleared() {
        super.onCleared()
        cancel()
        BaseWalletFragment.removeLoader()
        BaseWalletActivity.isShowLoadingLiveData.value?.clear()
        BaseWalletActivity.isShowLoadingLiveData.value = Stack()

        BaseWalletActivity.isShowErrorMessageLiveData.value?.clear()
        BaseWalletActivity.isShowErrorMessageLiveData.value = Stack()

        BaseWalletActivity.isShowSuccessMessageLiveData.value?.clear()
        BaseWalletActivity.isShowSuccessMessageLiveData.value = Stack()

        BaseWalletActivity.isShowInfoMessageLiveData.value?.clear()
        BaseWalletActivity.isShowInfoMessageLiveData.value = Stack()

        BaseWalletActivity.isShowConfirmationMessageLiveData.value?.clear()
        BaseWalletActivity.isShowConfirmationMessageLiveData.value = Stack()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    @Suppress("unused")
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [androidx.databinding.Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    override val coroutineContext = WalletApplication.getCoroutineContext()

    @Suppress("unused")
    fun isMultiplyLiveDataCompleted(vararg liveDates: LiveData<*>?): MediatorLiveData<Int> {
        val mediatorLiveData: MediatorLiveData<Int> = MediatorLiveData()
        for (liveData in liveDates) {
            liveData ?: continue
            val currentValue = mediatorLiveData.value ?: 0
            mediatorLiveData.value = currentValue + 1
            mediatorLiveData.addSource(liveData) {
                mediatorLiveData.removeSource(liveData)
                mediatorLiveData.value = mediatorLiveData.value?.minus(1)
            }
        }
        return mediatorLiveData
    }
}