package am.mil.walletapplication.history

import am.mil.domain.history.model.HistoryItem
import am.mil.walletapplication.databinding.ItemHistoryBinding
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class HistoryPagingAdapter(private val itemClick: (HistoryItem) -> Unit) :

    PagingDataAdapter<HistoryItem,
            HistoryPagingAdapter.HistoryViewHolder>(HistoryItemComparator) {

    private val items: MutableList<HistoryItem> = mutableListOf()
    private lateinit var context: Context
    private lateinit var layoutInflater: LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        layoutInflater = LayoutInflater.from(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: MutableList<HistoryItem>?) {
        this.items.clear()
        items?.let { this.items.addAll(items) }
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder(ItemHistoryBinding.inflate(layoutInflater, parent, false))

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.historyTitle.text = items[position].title
        holder.historyDesc.text = items[position].description
        holder.historyDate.text = items[position].transactionDate
    }

    inner class HistoryViewHolder(binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val historyTitle: TextView = binding.historyItemTitle
        val historyDesc: TextView = binding.historyItemDesc
        val historyDate: TextView = binding.historyItemDate
    }

    object HistoryItemComparator : DiffUtil.ItemCallback<HistoryItem>() {
        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem == newItem
        }
    }
}
