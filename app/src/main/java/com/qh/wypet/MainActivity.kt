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
        
        // 强制设置状态栏颜色为白色
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(android.R.color.white)
        
        // 设置状态栏图标为深色，适合白色背景
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        
        // 让系统自己管理内容区域，不要延伸到状态栏
        WindowCompat.setDecorFitsSystemWindows(window, true)
        
        // 导航栏可以保持透明
        window.navigationBarColor = android.graphics.Color.TRANSPARENT
        
        // 使用更现代的API设置系统UI可见性
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
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