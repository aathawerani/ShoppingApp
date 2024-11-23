package com.example.daraz4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daraz4.databinding.ProductItemBinding

class ProductItemAdapter(val clickListener:(product: Product) -> Unit) :
    ListAdapter<Product, ProductItemAdapter.ProductItemViewHolder>(ProductDiffItemCallback()){
    class ProductItemViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root){
        companion object{
            fun inflateFrom(parent: ViewGroup) : ProductItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductItemBinding.inflate(layoutInflater, parent, false)
                return ProductItemViewHolder(binding)
            }
        }
        fun bind(item: Product, clickListener:(product: Product) -> Unit)
        {
            binding.product = item
            binding.root.setOnClickListener{
                clickListener(item)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductItemViewHolder = ProductItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class ProductDiffItemCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product) =
        (oldItem.productId == newItem.productId)
    override fun areContentsTheSame(oldItem: Product, newItem: Product) =
        (oldItem == newItem)
}