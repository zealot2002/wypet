package com.qh.wypet.ui.encyclopedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.databinding.ItemEncyclopediaCategoryBinding

class EncyclopediaCategoryAdapter(
    private val onCategoryClick: (EncyclopediaCategory) -> Unit
) : ListAdapter<EncyclopediaCategory, EncyclopediaCategoryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEncyclopediaCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    inner class ViewHolder(private val binding: ItemEncyclopediaCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onCategoryClick(getItem(position))
                }
            }
        }

        fun bind(category: EncyclopediaCategory) {
            binding.categoryTitle.text = category.name
            binding.categoryDescription.text = category.description
            binding.categoryIcon.setImageResource(category.iconResId)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<EncyclopediaCategory>() {
        override fun areItemsTheSame(
            oldItem: EncyclopediaCategory,
            newItem: EncyclopediaCategory
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EncyclopediaCategory,
            newItem: EncyclopediaCategory
        ): Boolean {
            return oldItem == newItem
        }
    }
} 