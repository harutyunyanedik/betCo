package am.mil.data.menu.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MenuItemDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("parentId")
    val parentId: Any? = null,
    @SerializedName("parentNodeId")
    val parentNodeId: Any? = null,
    @SerializedName("svg")
    val svg: String? = null
) : Serializable
