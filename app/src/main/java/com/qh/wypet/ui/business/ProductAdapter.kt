package com.qh.wypet.ui.business

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qh.wypet.R
import com.qh.wypet.widgets.PriceView

class ProductAdapter(
    private val products: List<ProductModel>,
    private val onProductClick: (ProductModel) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.product_image)
        private val titleView: TextView = itemView.findViewById(R.id.product_name)
        private val priceView: PriceView = itemView.findViewById(R.id.product_price)
        private val shippingText: TextView = itemView.findViewById(R.id.shipping_text)
        
        init {
            // 添加点击监听器，通过回调通知Fragment
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onProductClick(products[position])
                }
            }
        }

        fun bind(product: ProductModel) {
            // 增强图片加载配置
            Glide.with(itemView.context)
                .load(product.imageUrl)
                .centerCrop()
                .into(imageView)
    
            // 设置标题和价格
            titleView.text = product.name
            
            // 将价格字符串转换为Double
            try {
                val priceDouble = product.price.toDoubleOrNull() ?: 0.0
                priceView.setPrice(priceDouble)
            } catch (e: NumberFormatException) {
                // 如果转换失败，使用0.0作为默认值
                priceView.setPrice(0.0)
            }
    
            // 设置包邮标签 - 现在统一隐藏
            shippingText.visibility = View.GONE
        }
    }
} 