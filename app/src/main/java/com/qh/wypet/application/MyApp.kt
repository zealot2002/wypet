package com.qh.wypet.application

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        
        // 配置Glide
        configureGlide()
    }
    
    private fun configureGlide() {
        Glide.init(this, GlideBuilder().apply {
            setDefaultRequestOptions(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 缓存所有版本的图片
                    .timeout(30000) // 30秒超时
            )
        })
    }

    companion object {
        lateinit var app:Application
    }
}