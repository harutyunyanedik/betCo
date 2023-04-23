package am.mil.data.net.history

import am.mil.data.HistoryPagingDataSource
import am.mil.domain.history.model.History
import am.mil.domain.history.repo.HistorySharedRepo
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class HistorySharedRepoImpl(private val dataSource: HistoryPagingDataSource) : HistorySharedRepo {
    override suspend fun getHistory(): Flow<PagingData<History.HistoryItem>> {
        return Pager(PagingConfig(10)) { dataSource }.flow.cachedIn(CoroutineScope(Dispatchers.IO))
    }
}