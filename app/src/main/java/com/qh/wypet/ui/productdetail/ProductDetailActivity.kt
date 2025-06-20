package com.qh.wypet.ui.productdetail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.qh.wypet.R
import com.qh.wypet.utils.ImageUrls
import java.text.NumberFormat
import java.util.Locale

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productImageAdapter: ProductImageAdapter
    private lateinit var commentsAdapter: CommentAdapter
    private lateinit var relatedProductsAdapter: RelatedProductsAdapter
    private var productId: String? = null

    // UI components
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var toolbarTitle: TextView
    
    // Auto-scroll for product images
    private val handler = Handler(Looper.getMainLooper())
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (::viewPager.isInitialized && productImageAdapter.itemCount > 0) {
                val nextItem = (viewPager.currentItem + 1) % productImageAdapter.itemCount
                viewPager.setCurrentItem(nextItem, true)
            }
            handler.postDelayed(this, AUTO_SCROLL_DELAY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        
        // Make the status bar transparent for immersive experience
        setupTransparentStatusBar()

        productId = intent.getStringExtra(EXTRA_PRODUCT_ID)

        setupToolbarAndScrollBehavior()
        setupNavigation()
        initProductDetail()
    }
    
    private fun setupTransparentStatusBar() {
        // Make status bar completely transparent
        window.statusBarColor = Color.TRANSPARENT
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 and above
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // For older versions
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            // Don't set light status bar initially to match the white text in the image
        }
    }
    
    private fun setupToolbarAndScrollBehavior() {
        appBarLayout = findViewById(R.id.appBarLayout)
        toolbarTitle = findViewById(R.id.toolbarTitle)
        
        // Hide toolbar title initially
        toolbarTitle.alpha = 0f
        
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val maxScroll = appBarLayout.totalScrollRange
            val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()
            
            // Fade in toolbar title as user scrolls
            toolbarTitle.alpha = percentage
            
            // Change status bar icons to dark when toolbar becomes visible
            if (percentage > 0.9f) {
                setLightStatusBar(true)
            } else {
                setLightStatusBar(false)
            }
        })
    }
    
    private fun setLightStatusBar(isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                if (isLight) {
                    it.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else {
                    it.setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }
        } else {
            @Suppress("DEPRECATION")
            if (isLight) {
                window.decorView.systemUiVisibility = (
                        window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
            } else {
                window.decorView.systemUiVisibility = (
                        window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }
    
    override fun onPause() {
        stopAutoScroll()
        super.onPause()
    }
    
    private fun startAutoScroll() {
        handler.postDelayed(autoScrollRunnable, AUTO_SCROLL_DELAY)
    }
    
    private fun stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
    }

    private fun setupNavigation() {
        // Handle back button
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            onBackPressed()
        }
        
        // Handle share button
        findViewById<ImageButton>(R.id.btnShare).setOnClickListener {
            shareProduct()
        }
    }

    private fun initProductDetail() {
        // For demo purposes, create mock data
        val product = createMockProductDetail()
        
        // Initialize product images
        setupProductImages(product.imageUrls)
        
        // Set product info
        val priceView = findViewById<com.qh.wypet.widgets.PriceView>(R.id.priceView)
        priceView.setPrice(product.price, product.originalPrice)
        
        findViewById<android.widget.TextView>(R.id.productTitle).text = product.title
        findViewById<android.widget.TextView>(R.id.productDescription).text = product.description
        findViewById<android.widget.TextView>(R.id.ratingText).text = product.rating.toString()
        
        // Setup comments
        setupComments(product.comments)
        
        // Setup related products
        setupRelatedProducts(product.relatedProducts)
        
        // Setup bottom actions
        setupBottomActions()
    }
    
    private fun setupProductImages(imageUrls: List<String>) {
        viewPager = findViewById(R.id.productImagePager)
        productImageAdapter = ProductImageAdapter(imageUrls)
        viewPager.adapter = productImageAdapter
        
        // Set up indicator dots
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.imageIndicator)
        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager) { _, _ -> }
        tabLayoutMediator.attach()
        
        // Set up page change listener to pause auto-scroll when user is interacting
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Reset auto-scroll timer when user manually changes page
                stopAutoScroll()
                startAutoScroll()
            }
        })
    }
    
    private fun setupComments(comments: List<CommentModel>) {
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.commentsRecyclerView)
        commentsAdapter = CommentAdapter(comments)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = commentsAdapter
        
        findViewById<android.widget.TextView>(R.id.commentCount).text = "(${comments.size})"
        findViewById<android.widget.TextView>(R.id.viewAllComments).setOnClickListener {
            // TODO: Navigate to all comments
            Toast.makeText(this, "View all comments", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupRelatedProducts(relatedProducts: List<RelatedProductModel>) {
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.relatedProductsRecyclerView)
        relatedProductsAdapter = RelatedProductsAdapter(relatedProducts) { product ->
            navigateToProductDetail(product.id)
        }
        
        // Use StaggeredGridLayoutManager with 2 columns for waterfall layout
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = relatedProductsAdapter
    }
    
    private fun setupBottomActions() {
        findViewById<android.widget.LinearLayout>(R.id.btnFavorite).setOnClickListener {
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show()
        }
        
        findViewById<android.widget.LinearLayout>(R.id.btnCustomerService).setOnClickListener {
            Toast.makeText(this, "Customer service", Toast.LENGTH_SHORT).show()
        }
        
        findViewById<android.widget.LinearLayout>(R.id.btnBuyNow).setOnClickListener {
            Toast.makeText(this, "Buy now", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createMockProductDetail(): ProductDetailModel {
        // Create mock product details
        return ProductDetailModel(
            id = "123",
            title = "猫咪之魂抱枕",
            description = "卖得很火的猫咪抱枕，可爱造型，柔软舒适，送礼自用两相宜",
            price = 49.99,
            originalPrice = 69.99,
            imageUrls = listOf(
                // Use more reliable image URLs
                ImageUrls.CAT_TOY_6,
                ImageUrls.CAT_TOY_7,
                ImageUrls.CAT_TOY_10
            ),
            rating = 9.3f,
            ratingCount = 235,
            comments = createMockComments(),
            relatedProducts = createMockRelatedProducts()
        )
    }
    
    private fun createMockComments(): List<CommentModel> {
        return listOf(
            CommentModel(
                id = "1",
                userName = "开心的橘猫",
                userAvatar = ImageUrls.USER_AVATAR_1,
                content = "非常可爱的抱枕，质量很好，推荐购买！",
                rating = 5f,
                date = "2023-09-15"
            ),
            CommentModel(
                id = "2",
                userName = "喵星人爱好者",
                userAvatar = ImageUrls.USER_AVATAR_2,
                content = "手感很好，猫咪表情超级可爱，朋友很喜欢这个礼物",
                rating = 5f,
                date = "2023-09-10"
            ),
            CommentModel(
                id = "3",
                userName = "毛绒控",
                userAvatar = ImageUrls.USER_AVATAR_3,
                content = "面料很舒服，做工也很精细，很满意的一次购物",
                rating = 4.5f,
                date = "2023-09-05"
            )
        )
    }
    
    private fun createMockRelatedProducts(): List<RelatedProductModel> {
        return listOf(
            RelatedProductModel(
                id = "201",
                title = "柴犬抱枕",
                imageUrl = ImageUrls.DOG_BAIDU_2,
                price = 59.99
            ),
            RelatedProductModel(
                id = "202",
                title = "熊猫公仔",
                imageUrl = ImageUrls.CAT_TOY_11,
                price = 69.99
            ),
            RelatedProductModel(
                id = "203",
                title = "猫咪项圈",
                imageUrl = ImageUrls.CAT_SUPPLIES_1,
                price = 29.99
            ),
            RelatedProductModel(
                id = "204",
                title = "宠物零食套装",
                imageUrl = ImageUrls.CAT_FOOD_3,
                price = 39.99
            ),
            RelatedProductModel(
                id = "205",
                title = "猫爬架",
                imageUrl = ImageUrls.CAT_TOY_5,
                price = 199.99
            ),
            RelatedProductModel(
                id = "206",
                title = "宠物梳子",
                imageUrl = ImageUrls.CAT_TOY_8,
                price = 19.99
            )
        )
    }
    
    private fun shareProduct() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "查看这个可爱的宠物产品")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "快来看看这个超可爱的猫咪抱枕！${productId?.let { "Product ID: $it" } ?: ""}")
        startActivity(Intent.createChooser(shareIntent, "分享产品"))
    }
    
    private fun navigateToProductDetail(productId: String) {
        val intent = newIntent(this, productId)
        startActivity(intent)
    }
    
    companion object {
        private const val EXTRA_PRODUCT_ID = "extra_product_id"
        private const val AUTO_SCROLL_DELAY = 3000L // 3 seconds for auto-scroll
        
        fun newIntent(context: Context, productId: String): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT_ID, productId)
            }
        }
    }
} 