package com.qh.wypet.ui.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R

class CategoryActivity : AppCompatActivity() {
    
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var subcategoryAdapter: SubcategoryAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        
        setupCategoryList()
        setupSubcategoryGrid()
    }
    
    private fun setupCategoryList() {
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.recycler_categories)
        categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        
        val categories = CategoryDataProvider.getCategories()
        categoryAdapter = CategoryAdapter(categories) { position ->
            // Update subcategory display when category is selected
            updateSubcategories(position)
        }
        
        categoryRecyclerView.adapter = categoryAdapter
    }
    
    private fun setupSubcategoryGrid() {
        val subcategoryRecyclerView = findViewById<RecyclerView>(R.id.recycler_subcategories)
        subcategoryRecyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3)
        
        // Initialize with first category's subcategories
        val subcategories = CategoryDataProvider.getCategories()[0].subcategories
        subcategoryAdapter = SubcategoryAdapter(subcategories)
        
        subcategoryRecyclerView.adapter = subcategoryAdapter
    }
    
    private fun updateSubcategories(categoryPosition: Int) {
        val category = CategoryDataProvider.getCategories()[categoryPosition]
        subcategoryAdapter.updateSubcategories(category.subcategories)
        
        // Update header title
        findViewById<androidx.appcompat.widget.AppCompatTextView>(R.id.text_subcategory_header).text = category.name
    }
} 