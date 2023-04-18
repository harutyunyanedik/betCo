package am.mil.data.net.history

import am.mil.domain.history.model.HistoryItem
import am.mil.domain.history.repo.HistorySharedRepo

class HistorySharedRepoImpl(private val dataSource: HistoryDataSource):HistorySharedRepo {
    override suspend fun getHistory(): List<HistoryItem> {
        TODO("Not yet implemented")
    }


}