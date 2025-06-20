package com.qh.wypet.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R

class CategoryAdapter(
    private var categories: List<Category>,
    private val onCategoryClick: (Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = 0

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.text_category_name)
        val categoryContainer: ConstraintLayout = itemView.findViewById(R.id.container_category)
        val selectionIndicator: View = itemView.findViewById(R.id.view_selection_indicator)

        init {
            itemView.setOnClickListener {
                val oldSelected = selectedPosition
                selectedPosition = adapterPosition
                
                notifyItemChanged(oldSelected)
                notifyItemChanged(selectedPosition)
                
                onCategoryClick(selectedPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.name
        
        // Update UI based on selected state
        if (position == selectedPosition) {
            holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
            holder.selectionIndicator.visibility = View.VISIBLE
            holder.categoryContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorCategorySelected))
        } else {
            holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorTextPrimary))
            holder.selectionIndicator.visibility = View.INVISIBLE
            holder.categoryContainer.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorCategoryUnselected))
        }
    }

    override fun getItemCount() = categories.size
} 