package com.qh.wypet.ui.productdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
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

        productId = intent.getStringExtra(EXTRA_PRODUCT_ID)

        initToolbar()
        initProductDetail()
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

    private fun initToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        
        // Handle back button
        toolbar.setNavigationOnClickListener { onBackPressed() }
        
        // Handle share button
        findViewById<android.widget.ImageView>(R.id.btnShare).setOnClickListener {
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
        recyclerView.layoutManager = GridLayoutManager(this, 3)
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
                ImageUrls.CAT_BAIDU_1,
                ImageUrls.CAT_2,
                ImageUrls.CAT_3
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
                imageUrl = ImageUrls.DOG_1,
                price = 59.99
            ),
            RelatedProductModel(
                id = "202",
                title = "熊猫公仔",
                imageUrl = ImageUrls.OTHER_PET_1,
                price = 69.99
            ),
            RelatedProductModel(
                id = "203",
                title = "猫咪项圈",
                imageUrl = ImageUrls.CAT_ACCESSORY_1,
                price = 29.99
            ),
            RelatedProductModel(
                id = "204",
                title = "宠物零食套装",
                imageUrl = ImageUrls.PET_FOOD_1,
                price = 39.99
            ),
            RelatedProductModel(
                id = "205",
                title = "猫爬架",
                imageUrl = ImageUrls.CAT_TOY_1,
                price = 199.99
            ),
            RelatedProductModel(
                id = "206",
                title = "宠物梳子",
                imageUrl = ImageUrls.GROOMING_1,
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