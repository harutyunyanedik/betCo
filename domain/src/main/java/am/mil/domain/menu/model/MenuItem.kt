package am.mil.domain.menu.model

import java.io.Serializable

data class MenuItem(
    var id: Long? = null,
    var childMenuItems: List<MenuItem>? = null,
    var svg: String? = null,
    var name: String? = null,
    var titleColor: String? = null,
    var iconTint: String? = null
): Serializable