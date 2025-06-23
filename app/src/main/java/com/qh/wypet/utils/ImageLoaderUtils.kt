package com.qh.wypet.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.qh.wypet.R
import com.qh.wypet.application.MyApp

/**
 * 统一管理图片加载的工具类
 */
object ImageLoaderUtils {
    
    // 默认使用drawable作为占位图，但会在init方法中替换为自定义占位图
    private var placeholderDrawable: Drawable? = null
    
    init {
        // 初始化时创建自定义占位图
        createPlaceholderDrawable()
    }
    
    /**
     * 创建带有"无忧宠服"logo的自定义占位图
     */
    private fun createPlaceholderDrawable() {
        try {
            // 使用Application Context确保不会泄露
            val context = MyApp.app
            val inflater = LayoutInflater.from(context)
            val placeholderView = inflater.inflate(R.layout.placeholder_logo, null)
            
            // 测量视图
            val measureWidth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            val measureHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            placeholderView.measure(measureWidth, measureHeight)
            
            // 布局视图
            placeholderView.layout(0, 0, placeholderView.measuredWidth, placeholderView.measuredHeight)
            
            // 创建bitmap
            val bitmap = Bitmap.createBitmap(
                placeholderView.measuredWidth, 
                placeholderView.measuredHeight, 
                Bitmap.Config.ARGB_8888
            )
            
            // 将视图绘制到bitmap
            val canvas = Canvas(bitmap)
            placeholderView.draw(canvas)
            
            // 创建drawable
            placeholderDrawable = BitmapDrawable(context.resources, bitmap)
        } catch (e: Exception) {
            // 如果出错，使用drawable资源作为备选
            placeholderDrawable = MyApp.app.getDrawable(R.drawable.placeholder_image)
        }
    }
    
    /**
     * 获取占位图
     */
    private fun getPlaceholder(): Drawable {
        return placeholderDrawable ?: MyApp.app.getDrawable(R.drawable.placeholder_image)!!
    }
    
    /**
     * 加载普通图片
     */
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(getPlaceholder())
            .error(getPlaceholder())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
    
    /**
     * 加载圆形图片（头像）
     */
    fun loadCircleImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(getPlaceholder())
            .error(getPlaceholder())
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
    
    /**
     * 加载图片并居中裁剪
     */
    fun loadCenterCropImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(getPlaceholder())
            .error(getPlaceholder())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
    
    /**
     * 加载图片并适应容器大小
     */
    fun loadFitCenterImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(getPlaceholder())
            .error(getPlaceholder())
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
    
    /**
     * 加载图片并添加监听器
     */
    fun loadImageWithListener(
        imageView: ImageView, 
        url: String?, 
        listener: RequestListener<Drawable>
    ) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(getPlaceholder())
            .error(getPlaceholder())
            .listener(listener)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
} 