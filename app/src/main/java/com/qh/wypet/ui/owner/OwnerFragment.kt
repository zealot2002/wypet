package com.qh.wypet.ui.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.qh.wypet.MainActivity
import com.qh.wypet.databinding.FragmentOwnerBinding
import com.qh.wypet.ui.base.BaseFragment

class OwnerFragment : BaseFragment() {

    private var _binding: FragmentOwnerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOwnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupStatusBarSpacing()
        setupProfileSection()
        setupKnowledgeCenter()
        setupQuickFunctions()
    }
    
    private fun setupStatusBarSpacing() {
        // 添加padding到顶部，考虑状态栏高度
        val statusBarHeight = getStatusBarHeight()
        binding.statusBarSpacing.layoutParams.height = statusBarHeight
        binding.statusBarSpacing.requestLayout()
    }
    
    private fun getStatusBarHeight(): Int {
        val resources = resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }
    
    private fun setupProfileSection() {
        binding.profileManagement.setOnClickListener {
            showToast("主人信息管理")
        }
        
        binding.accountSecurity.setOnClickListener {
            showToast("账户安全")
        }
        
        binding.cameraButton.setOnClickListener {
            showToast("更换头像")
        }
    }
    
    private fun setupKnowledgeCenter() {
        binding.encyclopediaCard.setOnClickListener {
            showToast("猫咪营养饲养百科")
        }
        
        binding.aiCard.setOnClickListener {
            showToast("AI问答")
        }
    }
    
    private fun setupQuickFunctions() {
        binding.catInfoFunction.setOnClickListener {
            showToast("猫咪基本信息管理")
        }
        
        binding.healthFunction.setOnClickListener {
            showToast("猫咪健康事件管理")
        }
        
        binding.addressFunction.setOnClickListener {
            showToast("收货地址管理")
        }
        
        binding.orderFunction.setOnClickListener {
            showToast("订单管理")
        }
        
        binding.giftFunction.setOnClickListener {
            showToast("礼品箱管理")
        }
        
        binding.servicesFunction.setOnClickListener {
            showToast("我的服务")
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = OwnerFragment()
    }
} 