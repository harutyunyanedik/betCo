package am.mil.data.net.history

import com.google.gson.annotations.SerializedName

data class HistoryDto(
    @SerializedName("transactions")
    val transactions: List<HistoryItemDto>? = null,
    @SerializedName("totalCount")
    val totalCount: Int? = null,

    ) {
    data class HistoryItemDto(
        @SerializedName("id")
        val id: String? = null,
        @SerializedName("transactionId")
        val transactionId: String? = null,
        @SerializedName("amount")
        val amount: Double? = null,
        @SerializedName("amountAfterTransaction")
        val amountAfterTransaction: Double? = null,
        @SerializedName("type")
        val type: Int? = null,
        @SerializedName("userWalletId")
        val userWalletId: Int? = null,
        @SerializedName("userWallet")
        val userWallet: String? = null,
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("createDate")
        val createDate: String? = null,
    )
}


