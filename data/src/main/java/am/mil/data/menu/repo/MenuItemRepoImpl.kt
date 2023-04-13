package am.mil.data.menu.repo

import am.mil.data.menu.mapper.MenuItemsMapper
import am.mil.data.net.WalletDataSource
import am.mil.domain.menu.repo.MenuItemRepo
import am.mil.domain.menu.model.MenuItem

class MenuItemRepoImpl(private val dataSource: WalletDataSource) : MenuItemRepo {

    override suspend fun getMenuItems(): List<MenuItem> {
        return dataSource.getMenuItems()?.let(MenuItemsMapper.menuItemsMapper) ?: emptyList()
    }
}