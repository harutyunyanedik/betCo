package am.mil.data.net

import am.mil.data.menu.dto.MenuItemDto
import retrofit2.http.GET

interface WalletDataSource {

    @GET("api/Menu")
    suspend fun getMenuItems(): List<MenuItemDto>?
}