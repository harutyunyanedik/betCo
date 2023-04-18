package am.mil.data.net.history

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HistoryItemDto(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("transactionDate")
    val transactionDate: String? = null,
) : Serializable
