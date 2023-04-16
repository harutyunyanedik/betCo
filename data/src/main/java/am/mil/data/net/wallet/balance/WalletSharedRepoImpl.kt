package am.mil.data.net.wallet.balance

import am.mil.data.net.wallet.WalletDataSource
import am.mil.data.wallet.BalanceMapper
import am.mil.domain.wallet.model.Balance
import am.mil.domain.wallet.repo.WalletSharedRepo

class WalletSharedRepoImpl(private val dataSource: WalletDataSource) : WalletSharedRepo {

    override suspend fun getBalance(): Balance {
        return dataSource.getBalance().data.let(BalanceMapper.balanceMapper)
    }
}