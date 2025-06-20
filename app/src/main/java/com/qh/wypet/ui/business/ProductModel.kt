package com.qh.wypet.ui.business

data class ProductModel(
    val id: String,
    val name: String,
    val price: String,
    val discount: String? = null,
    val hasShipping: Boolean = false,
    val imageUrl: String
) 