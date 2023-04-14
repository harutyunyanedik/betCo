package am.mil.data.net.categories

import retrofit2.http.GET

interface CategoriesDataSource {

    @GET("api/Menu")
    suspend fun getCategories(): List<CategoryItemDto>?
}