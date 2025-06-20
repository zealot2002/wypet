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
    
    // 保存所有一级fragment实例
    private lateinit var petFragment: PetFragment
    private lateinit var socialFragment: SocialFragment
    private lateinit var businessFragment: BusinessFragment
    private lateinit var ownerFragment: OwnerFragment
    
    // 当前显示的fragment
    private var currentFragment: Fragment? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // 移除全屏标志设置
        super.onCreate(savedInstanceState)
        
        // 隐藏标题栏
        supportRequestWindowFeature(android.view.Window.FEATURE_NO_TITLE)
        
        // 设置沉浸式状态栏
        setupImmersiveStatusBar()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化所有fragment
        initFragments()
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

    private fun initFragments() {
        // 创建fragment实例
        petFragment = PetFragment.newInstance()
        socialFragment = SocialFragment.newInstance()
        businessFragment = BusinessFragment.newInstance()
        ownerFragment = OwnerFragment.newInstance()
        
        // 获取FragmentManager
        val transaction = supportFragmentManager.beginTransaction()
        
        // 添加所有fragment到容器，但初始都隐藏
        transaction.add(R.id.fragment_container, petFragment).hide(petFragment)
        transaction.add(R.id.fragment_container, socialFragment).hide(socialFragment)
        transaction.add(R.id.fragment_container, businessFragment).hide(businessFragment)
        transaction.add(R.id.fragment_container, ownerFragment).hide(ownerFragment)
        
        // 提交事务
        transaction.commit()
    }

    private fun setupBottomNavigation() {
        // 设置图标不着色，使用原始图标颜色
        binding.bottomNavView.itemIconTintList = null
        // 设置文字颜色状态列表
        binding.bottomNavView.itemTextColor = resources.getColorStateList(R.color.bottom_nav_text_color, theme)
        
        binding.bottomNavView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_pet -> petFragment
                R.id.navigation_social -> socialFragment
                R.id.navigation_business -> businessFragment
                R.id.navigation_owner -> ownerFragment
                else -> petFragment
            }
            
            showFragment(fragment)
            true
        }
        
        // 默认选中宠物标签页
        binding.bottomNavView.selectedItemId = R.id.navigation_pet
    }
    
    private fun showFragment(fragment: Fragment) {
        if (fragment == currentFragment) {
            return
        }
        
        val transaction = supportFragmentManager.beginTransaction()
        
        // 隐藏当前fragment（如果存在）
        currentFragment?.let {
            transaction.hide(it)
        }
        
        // 显示选中的fragment
        transaction.show(fragment)
        
        // 更新当前fragment引用
        currentFragment = fragment
        
        // 提交事务
        transaction.commit()
    }
}