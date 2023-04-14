package am.mil.data.menu.mapper

import am.mil.data.base.Mapper
import am.mil.data.menu.dto.MenuItemDto
import am.mil.domain.menu.model.MenuItem

object MenuItemsMapper {

    val menuItemsMapper: Mapper<List<MenuItemDto>?, List<MenuItem>> = { response ->
        createMenuItemList(response)
    }

    private fun createMenuItemList(menuItemDto: List<MenuItemDto>?): List<MenuItem> {
        return menuItemDto?.map {
            createMenuItem(it).apply {
                if (!it.childMenuItems.isNullOrEmpty()) {
                    childMenuItems = createMenuItemList(it.childMenuItems)
                }
            }
        } ?: emptyList()
    }

    private fun createMenuItem(dto: MenuItemDto) = MenuItem(id = dto.id, svg = dto.svg, name = dto.name, titleColor = dto.textColor, iconTint = dto.iconColor)
}