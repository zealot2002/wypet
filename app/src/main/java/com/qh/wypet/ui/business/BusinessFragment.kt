package com.qh.wypet.ui.business

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.qh.wypet.R
import com.qh.wypet.databinding.FragmentBusinessBinding
import com.qh.wypet.ui.base.BaseFragment
import com.qh.wypet.ui.category.CategoryActivity
import com.qh.wypet.ui.productdetail.ProductDetailActivity
import com.qh.wypet.utils.ImageUrls

class BusinessFragment : BaseFragment() {

    private var _binding: FragmentBusinessBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<ProductModel>()
    private var currentSelectedCategoryId: Int = R.id.category_food

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusinessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductsRecyclerView()
        setupClickListeners()
        setupCategoryClickListeners()
        loadMockData()
        setupWhiteStatusBar()
    }
    
    private fun setupWhiteStatusBar() {
        // 商城页面不需要特殊处理状态栏，因为MainActivity已经设置了全局状态栏样式
        // 如果需要在这个页面有特殊的状态栏样式，可以在这里设置
    }

    private fun setupCategoryClickListeners() {
        val categoryIds = listOf(
            R.id.category_food,
            R.id.category_litter,
            R.id.category_bed,
            R.id.category_clean,
            R.id.category_toy,
            R.id.category_medical
        )
        
        categoryIds.forEach { id ->
            binding.root.findViewById<LinearLayout>(id)?.setOnClickListener {
                if (id != currentSelectedCategoryId) {
                    selectCategory(id)
                }
            }
        }
    }

    private fun selectCategory(categoryId: Int) {
        // 更新选中状态
        updateCategorySelection(categoryId)
        
        // 显示加载遮罩
        showLoading(true)
        
        // 1秒后隐藏加载遮罩
        Handler(Looper.getMainLooper()).postDelayed({
            showLoading(false)
        }, 300)
    }
    
    private fun updateCategorySelection(newCategoryId: Int) {
        // 取消当前选中项
        val currentCategory = binding.root.findViewById<LinearLayout>(currentSelectedCategoryId)
        val currentCategoryText = currentCategory?.getChildAt(0) as? TextView
        val currentCategoryIndicator = currentCategory?.getChildAt(1)
        
        currentCategoryText?.setTypeface(null, android.graphics.Typeface.NORMAL)
        currentCategoryIndicator?.visibility = View.INVISIBLE
        
        // 设置新选中项
        val newCategory = binding.root.findViewById<LinearLayout>(newCategoryId)
        val newCategoryText = newCategory?.getChildAt(0) as? TextView
        val newCategoryIndicator = newCategory?.getChildAt(1)
        
        newCategoryText?.setTypeface(null, android.graphics.Typeface.BOLD)
        newCategoryIndicator?.visibility = View.VISIBLE
        
        // 更新当前选中ID
        currentSelectedCategoryId = newCategoryId
    }
    
    private fun showLoading(show: Boolean) {
        binding.loadingOverlay.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setupProductsRecyclerView() {
        productAdapter = ProductAdapter(productList) { product ->
            // 处理产品点击，导航到产品详情页
            navigateToProductDetail(product.id)
        }
        
        binding.productsRecyclerView.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
    
    /**
     * 导航到产品详情页
     */
    private fun navigateToProductDetail(productId: String) {
        val intent = ProductDetailActivity.newIntent(requireContext(), productId)
        startActivity(intent)
    }
    
    private fun setupClickListeners() {
        // 设置搜索栏点击事件
        binding.searchContainer.setOnClickListener {
            Toast.makeText(context, "搜索宠物商品", Toast.LENGTH_SHORT).show()
        }
        
        // 设置相机按钮点击事件
        binding.btnCamera.setOnClickListener {
            Toast.makeText(context, "扫描宠物商品", Toast.LENGTH_SHORT).show()
        }
        
        // 设置搜索文本按钮点击事件
        binding.btnSearchText.setOnClickListener {
            Toast.makeText(context, "搜索宠物商品", Toast.LENGTH_SHORT).show()
        }
        
        // 设置更多分类按钮点击事件
        binding.btnMoreCategories.setOnClickListener {
            // 跳转到分类页面
            val intent = Intent(requireContext(), CategoryActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun loadMockData() {
        // 清除现有数据
        productList.clear()
        
        // 添加模拟数据 - 使用真实图片
        productList.addAll(
            listOf(
                // 猫粮类产品
                ProductModel(
                    id = "1",
                    name = "皇家Royal Canin 猫咪幼猫粮 2kg",
                    price = "199",
                    imageUrl = ImageUrls.CAT_FOOD_1
                ),
                ProductModel(
                    id = "2",
                    name = "冠能猫粮 成猫鸡肉配方 7kg",
                    price = "389",
                    discount = "50",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_FOOD_5
                ),
                ProductModel(
                    id = "13",
                    name = "喵趣猫粮 三文鱼成猫粮 1.5kg",
                    price = "99",
                    discount = "10",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_FOOD_2
                ),
                ProductModel(
                    id = "14",
                    name = "伟嘉猫粮 成猫牛肉味 10kg",
                    price = "299",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_FOOD_3
                ),
                ProductModel(
                    id = "15",
                    name = "希尔思猫粮 幼猫配方 2.5kg",
                    price = "259",
                    discount = "30",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_FOOD_4
                ),
                
                // 猫玩具类产品
                ProductModel(
                    id = "3",
                    name = "猫爬架 多层猫窝一体带猫抓板",
                    price = "399",
                    discount = "50",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_TOY_5
                ),
                ProductModel(
                    id = "4",
                    name = "猫咪电动逗猫棒 自动旋转",
                    price = "89",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_TOY_9
                ),
                ProductModel(
                    id = "16",
                    name = "猫隧道玩具 四通道可折叠",
                    price = "59",
                    discount = "5",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_TOY_1
                ),
                ProductModel(
                    id = "17",
                    name = "猫咪磨爪玩具 瓦楞纸耐磨",
                    price = "29",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_TOY_2
                ),
                ProductModel(
                    id = "18",
                    name = "猫咪激光逗猫棒 USB充电",
                    price = "49",
                    discount = "8",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_TOY_3
                ),
                
                // 猫咪用品类产品
                ProductModel(
                    id = "5",
                    name = "猫咪梳毛手套 去浮毛按摩梳",
                    price = "39",
                    discount = "10",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_SUPPLIES_2
                ),
                ProductModel(
                    id = "6",
                    name = "猫咪指甲剪 不锈钢安全指甲刀",
                    price = "28",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_SUPPLIES_9
                ),
                ProductModel(
                    id = "19",
                    name = "猫咪洗澡袋 防抓伤洗猫袋",
                    price = "59",
                    discount = "10",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_SUPPLIES_1
                ),
                ProductModel(
                    id = "20",
                    name = "猫咪牙刷套装 口腔清洁工具",
                    price = "45",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_SUPPLIES_3
                ),
                ProductModel(
                    id = "21",
                    name = "猫咪耳道清洁液 100ml",
                    price = "38",
                    discount = "5",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_SUPPLIES_4
                ),
                
                // 宠物健康类产品
                ProductModel(
                    id = "7",
                    name = "猫咪益生菌 肠胃调理 30包",
                    price = "129",
                    discount = "20",
                    hasShipping = true,
                    imageUrl = ImageUrls.PET_HEALTH_3
                ),
                ProductModel(
                    id = "8",
                    name = "猫咪化毛膏 去毛球营养膏 120g",
                    price = "68",
                    hasShipping = true,
                    imageUrl = ImageUrls.PET_HEALTH_7
                ),
                ProductModel(
                    id = "22",
                    name = "猫咪维生素营养粉 60g",
                    price = "85",
                    discount = "15",
                    hasShipping = true,
                    imageUrl = ImageUrls.PET_HEALTH_1
                ),
                ProductModel(
                    id = "23",
                    name = "猫咪关节保健品 鱼油丸 90粒",
                    price = "119",
                    hasShipping = true,
                    imageUrl = ImageUrls.PET_HEALTH_2
                ),
                ProductModel(
                    id = "24",
                    name = "猫咪护眼滴剂 天然草本 15ml",
                    price = "49",
                    discount = "5",
                    hasShipping = true,
                    imageUrl = ImageUrls.PET_HEALTH_4
                ),
                
                // 猫砂类产品
                ProductModel(
                    id = "9",
                    name = "豆腐猫砂 除臭无尘 6L",
                    price = "39",
                    discount = "5",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_LITTER_2
                ),
                ProductModel(
                    id = "10",
                    name = "膨润土猫砂 快速结团 10kg",
                    price = "65",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_LITTER_5
                ),
                ProductModel(
                    id = "25",
                    name = "水晶猫砂 无尘除臭 3.8L",
                    price = "45",
                    discount = "8",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_LITTER_1
                ),
                ProductModel(
                    id = "26",
                    name = "松木猫砂 天然环保 5kg",
                    price = "55",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_LITTER_3
                ),
                ProductModel(
                    id = "27",
                    name = "混合豆腐猫砂 绿茶味 7L",
                    price = "49",
                    discount = "7",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_LITTER_4
                ),
                
                // 猫薄荷类产品
                ProductModel(
                    id = "11",
                    name = "猫薄荷玩具 互动球 3个装",
                    price = "29",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_MINT_4
                ),
                ProductModel(
                    id = "12",
                    name = "猫薄荷干草 天然有机 50g",
                    price = "19",
                    discount = "3",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_MINT_9
                ),
                ProductModel(
                    id = "28",
                    name = "猫薄荷枕头玩具 可替换内芯",
                    price = "25",
                    discount = "5",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_MINT_1
                ),
                ProductModel(
                    id = "29",
                    name = "猫薄荷喷雾剂 50ml",
                    price = "32",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_MINT_2
                ),
                ProductModel(
                    id = "30",
                    name = "猫薄荷种植套装 DIY猫草种子",
                    price = "28",
                    discount = "4",
                    hasShipping = true,
                    imageUrl = ImageUrls.CAT_MINT_3
                )
            )
        )
        
        // 通知适配器数据变化
        productAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = BusinessFragment()
    }
} 