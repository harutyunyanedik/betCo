package am.mil.walletapplication.history

import am.mil.domain.history.model.History
import am.mil.domain.history.usecase.GetTransactionsHistoryUseCase
import am.mil.walletapplication.base.viewmodel.BaseObservableViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistoryMainTabViewModel(
    private val getHistoryUseCase: GetTransactionsHistoryUseCase
) : BaseObservableViewModel() {

    private val _historyItemsLiveData: MutableLiveData<PagingData<History.HistoryItem>> =
        MutableLiveData()
    val historyItemsLiveData: LiveData<PagingData<History.HistoryItem>>
        get() = _historyItemsLiveData

    fun getHistories() {
        viewModelScope.launch(coroutineContext) {
            getHistoryUseCase().onSuccess {
                it.collectLatest { historyPagingDat ->
                    _historyItemsLiveData.postValue(historyPagingDat)
                }
            }
            getHistoryUseCase().onError {  }
        }
    }
}