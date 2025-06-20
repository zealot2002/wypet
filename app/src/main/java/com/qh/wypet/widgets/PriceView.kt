package com.qh.wypet.widgets

import android.content.Context
import android.graphics.Paint
import android.text.SpannableString
import android.text.Spannable
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.qh.wypet.R
import java.text.NumberFormat
import java.util.Locale

/**
 * 自定义价格显示控件
 * 左边的￥符号比右边的价格数字小一些
 */
class PriceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val currentPriceView: TextView
    private val originalPriceView: TextView
    private val numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA)
    private val symbolSize = 0.8f // 符号大小为数字的80%

    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.widget_price_view, this, true)
        
        currentPriceView = findViewById(R.id.currentPrice)
        originalPriceView = findViewById(R.id.originalPrice)
        
        // Add strike-through to original price
        originalPriceView.paintFlags = originalPriceView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    /**
     * Set the price to display
     * 
     * @param currentPrice The current price to display
     * @param originalPrice The original price (optional) to display with strike-through
     */
    fun setPrice(currentPrice: Double, originalPrice: Double? = null) {
        val formattedCurrentPrice = numberFormat.format(currentPrice)
        currentPriceView.text = createPriceSpannable(formattedCurrentPrice)
        
        if (originalPrice != null && originalPrice > currentPrice) {
            val formattedOriginalPrice = numberFormat.format(originalPrice)
            originalPriceView.text = createPriceSpannable(formattedOriginalPrice)
            originalPriceView.visibility = VISIBLE
        } else {
            originalPriceView.visibility = GONE
        }
    }
    
    /**
     * Create a spannable string with smaller currency symbol
     */
    private fun createPriceSpannable(formattedPrice: String): SpannableString {
        val spannable = SpannableString(formattedPrice)
        // 人民币符号通常是第一个字符
        spannable.setSpan(
            RelativeSizeSpan(symbolSize), 
            0, 1, 
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }
} 