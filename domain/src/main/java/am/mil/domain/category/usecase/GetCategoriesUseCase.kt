package am.mil.domain.category.usecase

import am.mil.domain.base.DispatcherProvider
import am.mil.domain.base.UseCaseWithoutParams
import am.mil.domain.category.repo.CategoriesSharedRepo
import am.mil.domain.category.model.CategoryItem

class GetCategoriesUseCase(
    private val categoriesSharedRepo: CategoriesSharedRepo,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithoutParams<List<CategoryItem>>(dispatcherProvider.io) {

    override suspend fun execute(): List<CategoryItem> {
        return categoriesSharedRepo.getCategories()
    }
}