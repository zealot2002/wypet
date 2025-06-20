package com.qh.wypet.ui.base

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment

/**
 * 基础Fragment，处理沉浸式状态栏相关的边距
 */
abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 处理系统窗口插图，为状态栏留出空间
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            
            // 为视图顶部添加padding，避免内容被状态栏覆盖
            v.updatePadding(top = statusBarInsets.top)
            
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