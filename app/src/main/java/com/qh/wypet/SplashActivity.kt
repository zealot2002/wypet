package com.qh.wypet

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import android.widget.VideoView

class SplashActivity : AppCompatActivity() {
    
    private lateinit var videoView: VideoView
    private val timeoutDuration = 5000L // 5秒超时
    private val handler = Handler(Looper.getMainLooper())
    private val timeoutRunnable = Runnable {
        if (!isFinishing) {
            navigateToMainActivity()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 设置全屏
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        
        // 设置布局
        setContentView(R.layout.activity_splash)
        
        // 获取VideoView
        videoView = findViewById(R.id.splash_video_view)
        
        // 设置视频源
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splash
        videoView.setVideoURI(Uri.parse(videoPath))
        
        // 视频准备就绪后开始播放
        videoView.setOnPreparedListener { mp ->
            // 设置视频居中裁剪以填充屏幕
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
            
            // 设置循环播放
            mp.isLooping = false
            
            // 开始播放
            videoView.start()
        }
        
        // 视频播放完毕后跳转到主页面
        videoView.setOnCompletionListener {
            navigateToMainActivity()
        }
        
        // 设置视频播放错误的处理
        videoView.setOnErrorListener { _, _, _ ->
            navigateToMainActivity()
            true
        }
        
        // 设置超时处理，防止视频加载问题导致用户卡在启动页
        handler.postDelayed(timeoutRunnable, timeoutDuration)
    }
    
    private fun navigateToMainActivity() {
        // 移除超时回调
        handler.removeCallbacks(timeoutRunnable)
        
        // 打开主页面
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        
        // 应用淡出淡入动画
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        
        // 关闭启动页
        finish()
    }
    
    override fun onPause() {
        super.onPause()
        if (videoView.isPlaying) {
            videoView.pause()
        }
    }
    
    override fun onResume() {
        super.onResume()
        if (!videoView.isPlaying) {
            videoView.start()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // 移除超时回调
        handler.removeCallbacks(timeoutRunnable)
        videoView.stopPlayback()
    }
} 