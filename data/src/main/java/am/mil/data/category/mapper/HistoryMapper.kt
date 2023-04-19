package am.mil.data.category.mapper

import am.mil.data.base.Mapper
import am.mil.data.net.history.HistoryDto
import am.mil.domain.history.model.History

object HistoryMapper {

    val historiesMapper: Mapper<HistoryDto, History> = ::createHistory

    private fun createHistory(history: HistoryDto): History {
        return History(
            transactions = history.transactions?.map(::createHistoryItem) ?: arrayListOf(),
            totalCount = history.totalCount
        )
    }

    private fun createHistoryItem(historyItemDto: HistoryDto.HistoryItemDto) = History.HistoryItem(
        id = historyItemDto.id,
        transactionId = historyItemDto.transactionId,
        amount = historyItemDto.amount,
        amountAfterTransaction = historyItemDto.amountAfterTransaction,
        type = historyItemDto.type,
        userWallet = historyItemDto.userWallet,
        userWalletId = historyItemDto.userWalletId,
        description = historyItemDto.description,
        createDate = historyItemDto.createDate
    )
}