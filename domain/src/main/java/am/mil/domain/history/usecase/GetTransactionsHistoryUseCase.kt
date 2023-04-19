package am.mil.domain.history.usecase

import am.mil.domain.base.DispatcherProvider
import am.mil.domain.base.UseCaseWithoutParams
import am.mil.domain.history.model.History
import am.mil.domain.history.repo.HistorySharedRepo
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class GetTransactionsHistoryUseCase(
    private val historyRepo: HistorySharedRepo,
    dispatcherProvider: DispatcherProvider
) : UseCaseWithoutParams<Flow<PagingData<History.HistoryItem>>>(dispatcherProvider.io) {

    override suspend fun execute(): Flow<PagingData<History.HistoryItem>> {
        return historyRepo.getHistory()
    }
}