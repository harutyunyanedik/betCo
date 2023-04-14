package am.mil.walletapplication.category

import am.mil.domain.category.model.CategoryItemViewTypeEnum
import am.mil.domain.category.model.CategoryItem
import am.mil.walletapplication.base.utils.VectorDrawableCreator
import am.mil.walletapplication.databinding.ItemCategoryGridBinding
import am.mil.walletapplication.databinding.ItemCategoryLinearBinding
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(var itemViewType: CategoryItemViewTypeEnum = CategoryItemViewTypeEnum.GRID, private val itemClick: (CategoryItem) -> Unit) : RecyclerView.Adapter<CategoryAdapter.BaseViewHolder>() {

    private val items: MutableList<CategoryItem> = mutableListOf()
    private lateinit var context: Context
    private lateinit var layoutInflater: LayoutInflater

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getItemViewType(position: Int): Int = itemViewType.type

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(items: MutableList<CategoryItem>?) {
        this.items.clear()
        items?.let { this.items.addAll(items) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            CategoryItemViewTypeEnum.GRID.type -> CategoryGridViewHolder(ItemCategoryGridBinding.inflate(layoutInflater, parent, false))
            else -> CategoryLinearViewHolder(ItemCategoryLinearBinding.inflate(layoutInflater, parent, false))
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind(items[position])

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                itemClick.invoke(items[adapterPosition])
            }
        }

        abstract val categoryCardView: CardView
        abstract val categoryImage: ImageView
        abstract val categoryTitle: TextView

        open fun bind(item: CategoryItem) {
            item.svg?.let {
                val d = VectorDrawableCreator.getVectorDrawable(context, 24, 24, listOf(VectorDrawableCreator.PathData(it, Color.parseColor(item.iconTint))))
                categoryImage.background = d
            }
            categoryTitle.setTextColor(Color.parseColor(item.titleColor))
            categoryTitle.text = item.name
        }
    }

    inner class CategoryGridViewHolder(binding: ItemCategoryGridBinding) : BaseViewHolder(binding.root) {
        override val categoryCardView: CardView = binding.categoryCardView
        override val categoryImage: ImageView = binding.categoryImageView
        override val categoryTitle: TextView = binding.categoryNameTextView
    }

    inner class CategoryLinearViewHolder(binding: ItemCategoryLinearBinding) : BaseViewHolder(binding.root) {
        override val categoryCardView: CardView = binding.categoryCardView
        override val categoryImage: ImageView = binding.categoryImageView
        override val categoryTitle: TextView = binding.categoryNameTextView
    }
}