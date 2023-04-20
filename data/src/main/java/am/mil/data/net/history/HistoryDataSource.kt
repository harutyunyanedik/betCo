package am.mil.data.net.history

import am.mil.data.net.wallet.WalletDtoPacket
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface HistoryDataSource {

    @GET("api/Payment/TransactionHistory")
    suspend fun getHistory(
        @Query("pageSize") pageSize: Int, @Query("pageNumber") pageNumber: Int,
        @Header("User-Id") userId: Long = 3,
        @Header("Accept-Language") language: String = "en",
        @Header("X-Version") version: String = "1"
    ): WalletDtoPacket<HistoryDto>
}