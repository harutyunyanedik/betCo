package am.mil.data.net.wallet.balance

import am.mil.data.net.wallet.WalletDtoPacket
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BalanceDto(
    @SerializedName("availableBalance")
    val availableBalance: Double? = null,
    @SerializedName("balance")
    val balance: Double? = null,
    @SerializedName("currency")
    val currency: String? = null,
) : Serializable


class GetBalancePacket : WalletDtoPacket<BalanceDto>()