package am.mil.domain.wallet.model

import java.io.Serializable

data class Balance(
    val availableBalance: Double? = null,
    val balance: Double? = null,
    val currency: String? = null,
): Serializable