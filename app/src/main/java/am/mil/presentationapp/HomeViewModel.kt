package am.mil.presentationapp

import am.mil.domain.base.BetCoApiResultCallback
import am.mil.domain.cms.interactor.PromotionsUseCase
import am.mil.domain.cms.interactor.PromotionsUseCaseCallback
import am.mil.domain.cms.interactor.PromotionsUseCaseFlow
import am.mil.domain.cms.model.Promotion
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(
    private val promotionsUseCaseCallback: PromotionsUseCaseCallback,
    val promotionsUseCase: PromotionsUseCase,
    val promotionsUseCaseFlow: PromotionsUseCaseFlow
) : ViewModel() {

    private val _operationCompletedLiveData: MutableLiveData<Promotion?> = MutableLiveData()
    val operationCompletedLiveData: LiveData<Promotion?>
        get() = _operationCompletedLiveData

    private val _operationErrorLiveData: MutableLiveData<String?> = MutableLiveData()
    val operationErrorLiveData: LiveData<String?>
        get() = _operationErrorLiveData

    fun doOperationWithCallBack() {
        viewModelScope.launch {
            val params = PromotionsUseCaseCallback.Params(1, "eng", 1, "19742")
            promotionsUseCaseCallback(params, isShowLoader = true,
                resultCallBackBetCo = object : BetCoApiResultCallback<Promotion?> {
                    override fun onSuccess(response: Promotion?) {
                        _operationCompletedLiveData.value = response
                    }
                })
        }
    }


    fun doOperation() {
        viewModelScope.launch {
            val params = PromotionsUseCase.Params(1, "eng", 1, "19742")
            promotionsUseCase(params).onSuccess {
                _operationCompletedLiveData.value = it
            }.onError {
                _operationErrorLiveData.value = it.message
            }
        }
    }

    fun doOperationFlow() {
        viewModelScope.launch {
            val params = PromotionsUseCaseFlow.Params(1, "eng", 1, "19742")
            val a = promotionsUseCaseFlow(params)
            promotionsUseCaseFlow(params).collect {
                it.onSuccess { promotion ->
                    _operationCompletedLiveData.value = promotion
                }
            }
        }
    }
}