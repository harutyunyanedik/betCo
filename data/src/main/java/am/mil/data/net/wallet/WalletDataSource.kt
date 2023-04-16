package am.mil.data.net.wallet

import am.mil.data.net.wallet.balance.GetBalancePacket
import retrofit2.http.GET
import retrofit2.http.Header

interface WalletDataSource {

    @GET("api/Wallet/Balance")
    suspend fun getBalance(@Header("User-Id") userId: Long = 3): GetBalancePacket

}