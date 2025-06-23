package com.qh.wypet

import android.content.Intent
import android.os.Build
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
        
        // 设置默认状态栏
        setupDefaultStatusBar()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化所有fragment
        initFragments()
        setupBottomNavigation()
    }

    private fun setupDefaultStatusBar() {
        // 隐藏ActionBar
        supportActionBar?.hide()
        
        // 设置状态栏颜色为白色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(android.R.color.white)
        
        // 设置状态栏图标为深色，适合白色背景
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        
        // 让内容不要延伸到状态栏 (适用于默认页面)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        
        // 使用更现代的API设置系统UI可见性
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = true
        windowInsetsController.isAppearanceLightNavigationBars = true
    }
    
    // 此方法将被OwnerFragment调用
    fun enableImmersiveMode() {
        // 允许内容延伸到状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        // 设置状态栏为透明
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        
        // 设置状态栏图标为浅色，适合深色背景
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = false
    }
    
    // 此方法将被OwnerFragment在销毁时调用
    fun restoreDefaultStatusBar() {
        setupDefaultStatusBar()
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
            
            // 如果切换到Owner页面，启用沉浸式模式
            if (fragment is OwnerFragment) {
                enableImmersiveMode()
            } else {
                // 否则恢复默认状态栏
                restoreDefaultStatusBar()
            }
            
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