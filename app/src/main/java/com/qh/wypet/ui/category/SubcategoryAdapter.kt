package com.qh.wypet.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R

class SubcategoryAdapter(
    private var subcategories: List<Subcategory>
) : RecyclerView.Adapter<SubcategoryAdapter.SubcategoryViewHolder>() {

    inner class SubcategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subcategoryName: TextView = itemView.findViewById(R.id.text_subcategory_name)
        val subcategoryImage: ImageView = itemView.findViewById(R.id.image_subcategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_subcategory, parent, false)
        return SubcategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubcategoryViewHolder, position: Int) {
        val subcategory = subcategories[position]
        holder.subcategoryName.text = subcategory.name
        holder.subcategoryImage.setImageResource(subcategory.imageResId)
        
        holder.itemView.setOnClickListener {
            // Handle subcategory click
        }
    }

    override fun getItemCount() = subcategories.size

    fun updateSubcategories(newSubcategories: List<Subcategory>) {
        this.subcategories = newSubcategories
        notifyDataSetChanged()
    }
} 