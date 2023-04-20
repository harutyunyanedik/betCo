package am.mil.data.category.mapper

import am.mil.data.base.Mapper
import am.mil.data.net.categories.CategoryItemDto
import am.mil.domain.category.model.CategoryItem

object CategoriesMapper {

    val categoriesMapper: Mapper<List<CategoryItemDto>?, List<CategoryItem>> = { response ->
        createCategoryItemList(response)
    }

    private fun createCategoryItemList(categories: List<CategoryItemDto>?): List<CategoryItem> {
        return categories?.map {
            createCategoryItem(it).apply {
                if (!it.childCategoryItems.isNullOrEmpty()) {
                    childCategoryItems = createCategoryItemList(it.childCategoryItems)
                }
            }
        } ?: emptyList()
    }

    private fun createCategoryItem(dto: CategoryItemDto) = CategoryItem(
        id = dto.id, svg = dto.svg, name = dto.name, titleColor = dto.textColor, iconTint = dto.iconColor
    )
}