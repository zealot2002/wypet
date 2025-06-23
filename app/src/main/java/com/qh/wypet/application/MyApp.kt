package com.qh.wypet.application

import android.app.Application
import android.os.StrictMode
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
        
        // 配置网络请求策略
        configureNetworkPolicy()
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
    
    private fun configureNetworkPolicy() {
        // 允许主线程进行网络操作，用于开发测试，生产环境应使用协程或线程池
        val policy = StrictMode.ThreadPolicy.Builder()
            .permitAll()
            .build()
        StrictMode.setThreadPolicy(policy)
    }

    companion object {
        lateinit var app:Application
    }
}