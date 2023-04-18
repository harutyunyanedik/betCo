package am.mil.domain.history.repo

import am.mil.domain.history.model.HistoryItem

interface HistorySharedRepo {

    suspend fun getHistory(): List<HistoryItem>
}