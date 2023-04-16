package am.mil.domain.wallet.repo

import am.mil.domain.wallet.model.Balance

interface WalletSharedRepo {

    suspend fun getBalance() : Balance
}