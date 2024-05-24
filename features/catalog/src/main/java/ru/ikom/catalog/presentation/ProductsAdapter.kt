package ru.ikom.catalog.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.ikom.catalog.R
import ru.ikom.catalog.databinding.ProductItemLayoutBinding

class ProductsAdapter(
    private val onClick: (ProductUi) -> Unit,
    private val onAdd: (ProductUi) -> Unit
) : ListAdapter<ProductUi, ProductsAdapter.ViewHolder>(DiffProducts()) {

    inner class ViewHolder(private val layout: ProductItemLayoutBinding) : RecyclerView.ViewHolder(layout.root) {

        init {
            layout.root.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) onClick(getItem(adapterPosition))
            }

            layout.icAdded.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) onAdd(getItem(adapterPosition))
            }
        }

        fun bind(item: ProductUi) {
            layout.thumbnail.load(item.thumbnail)
            layout.tvTitle.text = item.title
            bindAdded(item)
        }

        fun bindAdded(item: ProductUi) {
            layout.icAdded.setImageResource(if (item.added) R.drawable.ic_remove else R.drawable.ic_add)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else holder.bindAdded(getItem(position))
    }
}

class DiffProducts : DiffUtil.ItemCallback<ProductUi>() {
    override fun areItemsTheSame(oldItem: ProductUi, newItem: ProductUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProductUi, newItem: ProductUi): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: ProductUi, newItem: ProductUi): Any? =
        oldItem.added != newItem.added

}