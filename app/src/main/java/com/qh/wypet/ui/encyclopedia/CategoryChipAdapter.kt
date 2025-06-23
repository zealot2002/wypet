package com.qh.wypet.ui.encyclopedia

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.qh.wypet.databinding.ItemEncyclopediaCategoryChipBinding

class CategoryChipAdapter(
    private val onCategoryClick: (EncyclopediaCategory) -> Unit
) : ListAdapter<EncyclopediaCategory, CategoryChipAdapter.ViewHolder>(DiffCallback) {

    // 当前选中的位置
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEncyclopediaCategoryChipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category, position == selectedPosition)
    }

    inner class ViewHolder(private val binding: ItemEncyclopediaCategoryChipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val oldSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(oldSelectedPosition)
                    notifyItemChanged(selectedPosition)
                    onCategoryClick(getItem(position))
                }
            }
        }

        fun bind(category: EncyclopediaCategory, isSelected: Boolean) {
            binding.categoryTitle.text = category.name
            binding.categoryIcon.setImageResource(category.iconResId)
            
            // 更新选中状态的样式
            val cardView = binding.root as MaterialCardView
            if (isSelected) {
                cardView.strokeColor = Color.parseColor("#03A9F4") // 蓝色边框
                cardView.strokeWidth = 2 // 加粗边框
                cardView.setCardBackgroundColor(Color.parseColor("#E1F5FE")) // 浅蓝背景
                binding.categoryTitle.setTextColor(Color.parseColor("#0288D1")) // 蓝色文字
                binding.categoryIcon.setColorFilter(Color.parseColor("#0288D1")) // 蓝色图标
            } else {
                cardView.strokeColor = Color.parseColor("#E0E0E0") // 默认边框
                cardView.strokeWidth = 1 // 默认边框宽度
                cardView.setCardBackgroundColor(Color.WHITE) // 白色背景
                binding.categoryTitle.setTextColor(Color.BLACK) // 默认文字颜色
                binding.categoryIcon.setColorFilter(Color.BLACK) // 默认图标颜色
            }
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