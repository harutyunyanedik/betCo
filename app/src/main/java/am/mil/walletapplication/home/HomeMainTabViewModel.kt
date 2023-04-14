package am.mil.walletapplication.home

import am.mil.domain.category.model.CategoryItem
import am.mil.domain.category.usecase.GetCategoriesUseCase
import am.mil.walletapplication.base.viewmodel.BaseObservableViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeMainTabViewModel(private val categoriesUseCase: GetCategoriesUseCase) : BaseObservableViewModel() {

    private val _categoryItemsLiveData: MutableLiveData<List<CategoryItem>> = MutableLiveData()
    val categoryItemsLiveData: LiveData<List<CategoryItem>>
        get() = _categoryItemsLiveData

    fun getCategories() {
        viewModelScope.launch(coroutineContext) {
            categoriesUseCase().onSuccess {
                _categoryItemsLiveData.value = it
            }.onError {
                println(it)
            }
        }
    }
}