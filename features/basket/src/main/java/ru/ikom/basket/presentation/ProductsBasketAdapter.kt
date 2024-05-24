package ru.ikom.basket.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.ikom.basket.databinding.ProductBasketItemLayoutBinding

class ProductsBasketAdapter(
    private val onClick: (ProductUi) -> Unit,
    private val onDelete: (ProductUi) -> Unit
) : ListAdapter<ProductUi, ProductsBasketAdapter.ViewHolder>(DiffProducts()) {

    inner class ViewHolder(private val layout: ProductBasketItemLayoutBinding) : RecyclerView.ViewHolder(layout.root) {

        init {
            layout.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) onClick(getItem(adapterPosition))
            }

            layout.icDelete.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) onDelete(getItem(adapterPosition))
            }
        }

        fun bind(item: ProductUi) {
            layout.thumbnail.load(item.thumbnail)
            layout.tvTitle.text = item.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductBasketItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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