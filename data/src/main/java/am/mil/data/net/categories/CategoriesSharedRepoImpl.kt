package am.mil.data.net.categories

import am.mil.data.category.mapper.CategoriesMapper
import am.mil.domain.category.repo.CategoriesSharedRepo
import am.mil.domain.category.model.CategoryItem

class CategoriesSharedRepoImpl(private val dataSource: CategoriesDataSource) : CategoriesSharedRepo {

    override suspend fun getCategories(): List<CategoryItem> {
        return dataSource.getCategories()?.let(CategoriesMapper.categoriesMapper) ?: emptyList()
    }
}