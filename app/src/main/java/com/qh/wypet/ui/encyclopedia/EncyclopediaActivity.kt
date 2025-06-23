package com.qh.wypet.ui.encyclopedia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.qh.wypet.R
import com.qh.wypet.databinding.ActivityEncyclopediaBinding
import com.qh.wypet.utils.ImageUrls

class EncyclopediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEncyclopediaBinding
    private lateinit var categoryAdapter: CategoryChipAdapter
    private lateinit var topicAdapter: TopicAdapter
    private lateinit var articleAdapter: ArticleAdapter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, EncyclopediaActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEncyclopediaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 启用沉浸式状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setupInsets()
        
        setupSearchBar()
        setupCategoryRecyclerView()
        setupTopicRecyclerView()
        setupArticleRecyclerView()
        setupViewAllButtons()
        setupBackButton()
    }
    
    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            
            // 调整顶部内边距以适应状态栏
            binding.appBar.updatePadding(top = insets.top)
            
            // 调整底部内边距以适应导航栏
            binding.scrollView.updatePadding(bottom = insets.bottom)
            
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setupBackButton() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupSearchBar() {
        binding.searchBar.setOnClickListener {
            // 这里可以启动搜索页面，或者展开高级搜索选项
            Toast.makeText(this, "搜索功能即将上线", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupCategoryRecyclerView() {
        categoryAdapter = CategoryChipAdapter { category ->
            // 处理分类点击事件
            Toast.makeText(this, "选择了${category.name}分类", Toast.LENGTH_SHORT).show()
        }
        
        binding.categoryRecycler.apply {
            adapter = categoryAdapter
            // 横向滚动
            layoutManager = LinearLayoutManager(
                this@EncyclopediaActivity, 
                LinearLayoutManager.HORIZONTAL, 
                false
            )
        }
        
        // 加载分类数据
        categoryAdapter.submitList(EncyclopediaDataProvider.getCategories())
    }
    
    private fun setupTopicRecyclerView() {
        topicAdapter = TopicAdapter { topic ->
            // 处理专题点击事件
            Toast.makeText(this, "查看${topic.title}专题", Toast.LENGTH_SHORT).show()
            // 可以启动专题详情页
            // TopicDetailActivity.start(this, topic)
        }
        
        binding.topicRecycler.apply {
            adapter = topicAdapter
            // 横向滚动
            layoutManager = LinearLayoutManager(
                this@EncyclopediaActivity, 
                LinearLayoutManager.HORIZONTAL, 
                false
            )
        }
        
        // 加载专题数据
        topicAdapter.submitList(EncyclopediaDataProvider.getHotTopics())
    }
    
    private fun setupArticleRecyclerView() {
        articleAdapter = ArticleAdapter { article ->
            // 处理文章点击事件，跳转到文章详情页
            ArticleDetailActivity.start(this, article.id)
        }
        
        binding.articleRecycler.apply {
            adapter = articleAdapter
            // 纵向滚动
            layoutManager = LinearLayoutManager(this@EncyclopediaActivity)
        }
        
        // 加载文章数据
        articleAdapter.submitList(EncyclopediaDataProvider.getLatestArticles())
    }
    
    private fun setupViewAllButtons() {
        binding.viewAllTopics.setOnClickListener {
            Toast.makeText(this, "查看全部专题", Toast.LENGTH_SHORT).show()
            // 可以启动全部专题页面
            // AllTopicsActivity.start(this)
        }
        
        binding.viewAllArticles.setOnClickListener {
            Toast.makeText(this, "查看全部文章", Toast.LENGTH_SHORT).show()
            // 可以启动全部文章页面
            // AllArticlesActivity.start(this)
        }
    }
} 