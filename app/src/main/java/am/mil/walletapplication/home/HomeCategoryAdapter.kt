package am.mil.walletapplication.home

import am.mil.domain.menu.model.MenuItem
import am.mil.walletapplication.base.utils.VectorDrawableCreator
import am.mil.walletapplication.databinding.ItemHomeCategoryBinding
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class HomeCategoryAdapter(private val itemClick: (MenuItem) -> Unit) : RecyclerView.Adapter<HomeCategoryAdapter.BaseViewHolder>() {

    private val items: MutableList<MenuItem> = mutableListOf()
    private lateinit var context: Context
    private lateinit var layoutInflater: LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        layoutInflater = LayoutInflater.from(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: MutableList<MenuItem>?) {
        this.items.clear()
        items?.let { this.items.addAll(items) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        HomeCategoryViewHolder(ItemHomeCategoryBinding.inflate(layoutInflater, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind(items[position])

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(menuItem: MenuItem)
    }

    inner class HomeCategoryViewHolder(private val binding: ItemHomeCategoryBinding) : BaseViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemClick.invoke(items[adapterPosition])
            }
        }

        override fun bind(menuItem: MenuItem) {
            menuItem.svg?.let {
                val d = VectorDrawableCreator.getVectorDrawable(context, 24, 24, listOf(VectorDrawableCreator.PathData(it, Color.parseColor("#123456"))))
                binding.categoryImageView.background = d
            }
            binding.categoryNameTextView.text = menuItem.name
        }
    }
}