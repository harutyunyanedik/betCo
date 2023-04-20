package am.mil.data

import am.mil.data.history.mapper.HistoryMapper
import am.mil.data.net.history.HistoryDataSource
import am.mil.domain.history.model.History
import androidx.paging.PagingSource

class HistoryPagingDataSource(private val dataSource: HistoryDataSource) :
    PagingSource<Int, History.HistoryItem>() {
    companion object {
        private const val PAGE_SIZE = 10
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, History.HistoryItem> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response: History? = dataSource.getHistory(
                PAGE_SIZE, nextPageNumber
            ).data?.let(HistoryMapper.historiesMapper)
            val totalPage: Int =
                if ((response?.totalCount?.div(PAGE_SIZE) ?: 0) % 2 > 0) (response?.totalCount?.div(
                    PAGE_SIZE
                ) ?: 0) + 1 else response?.totalCount?.div(PAGE_SIZE) ?: 1
            LoadResult.Page(
                data = response?.transactions ?: arrayListOf(),
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < totalPage) totalPage + 1 else null

            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}