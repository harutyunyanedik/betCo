package am.mil.walletapplication.history

import am.mil.domain.history.model.History
import am.mil.walletapplication.databinding.ItemHistoryBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class HistoryPagingAdapter(private val itemClick: (History.HistoryItem) -> Unit) :

    PagingDataAdapter<History.HistoryItem,
            HistoryPagingAdapter.HistoryViewHolder>(HistoryItemComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        getItem(position)?.let { historyItem ->
            holder.binding.historyItemDesc.text = historyItem.description
            holder.binding.historyItemDate.text = historyItem.createDate
            holder.binding.root.setOnClickListener {
                itemClick.invoke(historyItem)
            }
        }
    }

    inner class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    object HistoryItemComparator : DiffUtil.ItemCallback<History.HistoryItem>() {
        override fun areItemsTheSame(
            oldItem: History.HistoryItem,
            newItem: History.HistoryItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: History.HistoryItem,
            newItem: History.HistoryItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
