package am.mil.walletapplication.home

import am.mil.domain.menu.model.MenuItem
import am.mil.domain.menu.usecase.GetMenuItemsUseCase
import am.mil.walletapplication.base.viewmodel.BaseObservableViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeMainTabViewModel(private val menuItemsUseCase: GetMenuItemsUseCase) : BaseObservableViewModel() {

    private val _menuItemsLiveData: MutableLiveData<List<MenuItem>> = MutableLiveData()
    val menuItemsLiveData: LiveData<List<MenuItem>>
        get() = _menuItemsLiveData

    fun getMenuItems() {
        viewModelScope.launch(coroutineContext) {
            menuItemsUseCase().onSuccess {
                _menuItemsLiveData.value = it
            }.onError {
                println(it)
            }
        }
    }
}