package am.mil.data.wallet

import am.mil.data.base.Mapper
import am.mil.data.net.wallet.balance.BalanceDto
import am.mil.domain.wallet.model.Balance

object BalanceMapper {

    val balanceMapper: Mapper<BalanceDto?, Balance> = { response ->
        Balance(response?.availableBalance, response?.balance, response?.currency)
    }
}