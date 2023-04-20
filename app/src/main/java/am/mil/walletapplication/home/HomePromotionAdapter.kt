package am.mil.walletapplication.home

import am.mil.walletapplication.R
import am.mil.walletapplication.databinding.ItemHomePromotionBinding
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class HomePromotionAdapter(private val itemClick: (String) -> Unit) : RecyclerView.Adapter<HomePromotionAdapter.BaseViewHolder>() {

    private val items: MutableList<String> = mutableListOf()
    private lateinit var context: Context
    private lateinit var layoutInflater: LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        layoutInflater = LayoutInflater.from(context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: MutableList<String>?) {
        this.items.clear()
        items?.let { this.items.addAll(items) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder = HomePromotionAViewHolder(ItemHomePromotionBinding.inflate(layoutInflater, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind(items[position])

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: String)
    }

    inner class HomePromotionAViewHolder(private val binding: ItemHomePromotionBinding) : BaseViewHolder(binding.root) {

        private val roundedCornersTransformation = RoundedCornersTransformation(
            context.resources.getDimensionPixelSize(R.dimen._6dp), 0, RoundedCornersTransformation.CornerType.ALL
        )

        init {
            itemView.setOnClickListener {
                itemClick.invoke(items[adapterPosition])
            }
        }

        override fun bind(item: String) {
            Glide.with(context).load(item).into(binding.promotionImageView)
        }
    }
}