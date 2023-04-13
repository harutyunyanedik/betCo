package am.mil.data.menu.mapper

import am.mil.data.base.Mapper
import am.mil.data.menu.dto.MenuItemDto
import am.mil.domain.menu.model.MenuItem

object MenuItemsMapper {

    val menuItemsMapper: Mapper<List<MenuItemDto>?, List<MenuItem>> = { response ->
        response?.map { dto ->
            MenuItem(id = dto.id, name = dto.name, svg = dto.svg, parentId = dto.parentId, parentNodeId = dto.parentNodeId)
        } ?: emptyList()
    }
}