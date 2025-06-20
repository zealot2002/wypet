package com.qh.wypet.ui.category

data class Category(
    val id: Int,
    val name: String,
    val isSelected: Boolean = false,
    val subcategories: List<Subcategory> = emptyList()
)

data class Subcategory(
    val id: Int,
    val name: String,
    val imageResId: Int
) 