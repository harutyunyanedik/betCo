package am.mil.domain.history.repo

import am.mil.domain.history.model.History
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface HistorySharedRepo {

    suspend fun getHistory(): Flow<PagingData<History.HistoryItem>>
}