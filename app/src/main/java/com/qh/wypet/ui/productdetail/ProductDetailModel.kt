package com.qh.wypet.ui.productdetail

data class ProductDetailModel(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val originalPrice: Double? = null,
    val imageUrls: List<String>,
    val rating: Float,
    val ratingCount: Int,
    val comments: List<CommentModel> = emptyList(),
    val relatedProducts: List<RelatedProductModel> = emptyList()
)

data class CommentModel(
    val id: String,
    val userName: String,
    val userAvatar: String,
    val content: String,
    val rating: Float,
    val date: String,
    val images: List<String> = emptyList()
)

data class RelatedProductModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val price: Double
) 