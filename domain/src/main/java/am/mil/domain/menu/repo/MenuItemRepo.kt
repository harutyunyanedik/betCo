package am.mil.domain.menu.repo

import am.mil.domain.menu.model.MenuItem

interface MenuItemRepo {

    suspend fun getMenuItems(): List<MenuItem>
}