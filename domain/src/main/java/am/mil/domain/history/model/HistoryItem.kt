package am.mil.domain.history.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class History(
    val transactions: List<HistoryItem>? = null,
    val totalCount: Int? = null,
) : Parcelable {
    @Parcelize
    data class HistoryItem(
        val id: String? = null,
        val transactionId: String? = null,
        val amount: Double? = null,
        val amountAfterTransaction: Double? = null,
        val type: Int? = null,
        val userWalletId: Int? = null,
        val userWallet: String? = null,
        val description: String? = null,
        val createDate: String? = null,
    ) : Parcelable
}
