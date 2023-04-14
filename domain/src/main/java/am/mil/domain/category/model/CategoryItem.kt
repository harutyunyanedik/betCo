package am.mil.domain.category.model

import java.io.Serializable

data class CategoryItem(
    var id: Long? = null,
    var childCategoryItems: List<CategoryItem>? = null,
    var svg: String? = null,
    var name: String? = null,
    var titleColor: String? = null,
    var iconTint: String? = null
): Serializable