package com.qh.wypet.ui.base

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

/**
 * 基础Fragment
 */
abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 系统现在会自动处理状态栏区域，不需要手动添加padding
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            
            // 允许fragment子类处理自己的window insets
            onApplyWindowInsets(v, statusBarInsets.top)
            
            insets
        }
    }
    
    /**
     * 子类可以重写此方法以应用自己的window insets处理逻辑
     * @param view Fragment的根视图
     * @param statusBarHeight 状态栏的高度（以像素为单位）
     */
    protected open fun onApplyWindowInsets(view: View, statusBarHeight: Int) {
        // 默认实现为空，子类可根据需要重写
    }
} 