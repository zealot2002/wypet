package com.qh.wypet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.qh.wypet.R
import com.qh.wypet.databinding.ActivityMainBinding
import com.qh.wypet.ui.business.BusinessFragment
import com.qh.wypet.ui.category.CategoryActivity
import com.qh.wypet.ui.owner.OwnerFragment
import com.qh.wypet.ui.pet.PetFragment
import com.qh.wypet.ui.social.SocialFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // 移除全屏标志设置
        super.onCreate(savedInstanceState)
        
        // 隐藏标题栏
        supportRequestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        
        // 设置沉浸式状态栏
        setupImmersiveStatusBar()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
    }

    private fun setupImmersiveStatusBar() {
        // 隐藏ActionBar
        supportActionBar?.hide()
        
        // 设置布局延伸到状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        
        // 让内容延伸到状态栏和导航栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        // 状态栏设置为透明
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        
        // 添加沉浸式标志（适用于Android 5.0及以上）
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        
        // 获取WindowInsetsController来控制系统栏的外观
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        
        // 设置状态栏图标为深色(为浅色背景准备)
        windowInsetsController.isAppearanceLightStatusBars = true
        windowInsetsController.isAppearanceLightNavigationBars = true
        
        // 防止系统手势冲突（Android 10及以上）
        windowInsetsController.systemBarsBehavior = 
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    private fun setupBottomNavigation() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_pet -> PetFragment.newInstance()
                R.id.navigation_social -> SocialFragment.newInstance()
                R.id.navigation_business -> BusinessFragment.newInstance()
                R.id.navigation_owner -> OwnerFragment.newInstance()
                else -> SocialFragment.newInstance()
            }
            
            replaceFragment(fragment)
            true
        }
    }
    
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}