package am.mil.domain.category.repo

import am.mil.domain.category.model.CategoryItem

interface CategoriesSharedRepo {

    suspend fun getCategories(): List<CategoryItem>
}