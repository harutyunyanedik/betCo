package am.mil.data.net.categories

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryItemDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("parentId")
    val parentId: Any? = null,
    @SerializedName("parentNodeId")
    val parentNodeId: Any? = null,
    @SerializedName("menuItems")
    val childCategoryItems: List<CategoryItemDto>? = null,
    @SerializedName("svg")
    val svg: String? = null,
    @SerializedName("textColor")
    val textColor: String? = null,
    @SerializedName("iconColor")
    val iconColor: String? = null
) : Serializable
