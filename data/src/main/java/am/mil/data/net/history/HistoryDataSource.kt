package am.mil.data.net.history

import am.mil.data.net.categories.CategoryItemDto
import retrofit2.http.GET

interface HistoryDataSource {

    @GET("api/History")
    suspend fun getHistory(): List<HistoryItemDto>?
}