package am.mil.domain.history.model

import java.io.Serializable

data class HistoryItem(
    val id: Long? = null,
    val title: String? = null,
    val description: String? = null,
    val transactionDate: String? = null
) : Serializable
