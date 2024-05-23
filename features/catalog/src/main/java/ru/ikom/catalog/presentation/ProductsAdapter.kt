package ru.ikom.catalog.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.ikom.catalog.databinding.ProductItemLayoutBinding

class ProductsAdapter(
    private val onClick: (ProductUi) -> Unit
) : ListAdapter<ProductUi, ProductsAdapter.ViewHolder>(DiffProducts()) {

    inner class ViewHolder(private val layout: ProductItemLayoutBinding) : RecyclerView.ViewHolder(layout.root) {

        init {
            layout.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) onClick(getItem(adapterPosition))
            }
        }

        fun bind(item: ProductUi) {
            layout.thumbnail.load(item.thumbnail)
            layout.tvTitle.text = item.title
            layout.tvCategory.text = item.category
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffProducts : DiffUtil.ItemCallback<ProductUi>() {
    override fun areItemsTheSame(oldItem: ProductUi, newItem: ProductUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProductUi, newItem: ProductUi): Boolean =
        oldItem == newItem

}