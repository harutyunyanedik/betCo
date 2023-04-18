package am.mil.data.net.history

import retrofit2.http.GET

interface HistoryDataSource {

    @GET("api/Payment/TransactionHistory")
    suspend fun getHistory(): List<HistoryItemDto>?
}