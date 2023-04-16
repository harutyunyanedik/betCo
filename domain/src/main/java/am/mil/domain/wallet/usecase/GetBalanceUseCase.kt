package am.mil.domain.wallet.usecase

import am.mil.domain.base.DispatcherProvider
import am.mil.domain.base.UseCaseWithoutParams
import am.mil.domain.wallet.model.Balance
import am.mil.domain.wallet.repo.WalletSharedRepo

class GetBalanceUseCase(
    private val repo: WalletSharedRepo,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithoutParams<Balance>(dispatcherProvider.io) {

    override suspend fun execute(): Balance = repo.getBalance()
}