package am.mil.domain.menu.usecase

import am.mil.domain.base.DispatcherProvider
import am.mil.domain.base.UseCaseWithoutParams
import am.mil.domain.menu.repo.MenuItemRepo
import am.mil.domain.menu.model.MenuItem

class GetMenuItemsUseCase(
    private val menuItemGateway: MenuItemRepo,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithoutParams<List<MenuItem>>(dispatcherProvider.io) {

    override suspend fun execute(): List<MenuItem> {
        return menuItemGateway.getMenuItems()
    }
}