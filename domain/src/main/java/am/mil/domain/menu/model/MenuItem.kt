package am.mil.domain.menu.model

data class MenuItem(
    val id: Long?,
    val menuItems: List<MenuItem>? = null,
    val svg: String? = null,
    val name: String? = null,
    val parentId: Any? = null,
    val parentNodeId: Any? = null,
)