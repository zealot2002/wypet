package com.qh.wypet.ui.productdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qh.wypet.R
import com.qh.wypet.widgets.PriceView
import java.text.NumberFormat
import java.util.Locale

class RelatedProductsAdapter(
    private val relatedProducts: List<RelatedProductModel>,
    private val onProductClick: (RelatedProductModel) -> Unit
) : RecyclerView.Adapter<RelatedProductsAdapter.RelatedProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return RelatedProductViewHolder(view, onProductClick)
    }

    override fun onBindViewHolder(holder: RelatedProductViewHolder, position: Int) {
        holder.bind(relatedProducts[position])
    }

    override fun getItemCount(): Int = relatedProducts.size

    inner class RelatedProductViewHolder(
        itemView: View,
        private val onProductClick: (RelatedProductModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.product_image)
        private val titleView: TextView = itemView.findViewById(R.id.product_name)
        private val priceView: PriceView = itemView.findViewById(R.id.product_price)
        private val shippingText: TextView = itemView.findViewById(R.id.shipping_text)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onProductClick(relatedProducts[position])
                }
            }
        }

        fun bind(product: RelatedProductModel) {
            titleView.text = product.title
            
            // 设置价格
            priceView.setPrice(product.price)
            
            // 默认隐藏包邮标签
            shippingText.visibility = View.GONE

            Glide.with(itemView.context)
                .load(product.imageUrl)
                .centerCrop()
                .into(imageView)
        }
    }
} 