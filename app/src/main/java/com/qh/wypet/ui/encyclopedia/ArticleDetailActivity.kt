package com.qh.wypet.ui.encyclopedia

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.qh.wypet.R
import com.qh.wypet.databinding.ActivityArticleDetailBinding
import com.qh.wypet.utils.ImageLoaderUtils
import com.qh.wypet.utils.ImageUrls

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding
    private lateinit var paragraphAdapter: ArticleParagraphAdapter
    
    // 当前文章ID
    private var articleId: String = ""
    
    companion object {
        private const val EXTRA_ARTICLE_ID = "extra_article_id"
        
        fun start(context: Context, articleId: String) {
            val intent = Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE_ID, articleId)
            }
            context.startActivity(intent)
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // 获取文章ID
        articleId = intent.getStringExtra(EXTRA_ARTICLE_ID) ?: ""
        
        // 设置沉浸式状态栏
        setupWindow()
        
        // 设置工具栏
        setupToolbar()
        
        // 设置适配器
        setupAdapter()
        
        // 加载文章数据
        loadArticleData()
        
        // 设置底部按钮点击事件
        setupBottomActions()
    }
    
    private fun setupWindow() {
        // 设置沉浸式状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        
        // 处理系统窗口边距
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.appBar.updatePadding(top = insets.top)
            
            // 确保导航图标在初始化时可见
            binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, android.R.color.white))
            
            windowInsets
        }
    }
    
    private fun setupToolbar() {
        // 设置左侧返回箭头图标
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)?.apply {
            setTint(ContextCompat.getColor(this@ArticleDetailActivity, android.R.color.white))
        }
        
        // 设置返回按钮点击事件
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        
        // 确保导航图标可见
        binding.toolbar.post {
            binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, android.R.color.white))
        }
        
        // 监听滚动来改变工具栏背景和图标颜色
        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > 200) {
                // 滚动后的样式
                binding.appBar.setBackgroundColor(Color.WHITE)
                binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, android.R.color.black))
                
                // 更新中央标题文本颜色
                val titleTextView = binding.toolbar.getChildAt(0) as? TextView
                titleTextView?.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            } else {
                // 初始样式
                binding.appBar.setBackgroundColor(Color.parseColor("#80000000"))  // 半透明黑色背景
                binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, android.R.color.white))
                
                // 更新中央标题文本颜色
                val titleTextView = binding.toolbar.getChildAt(0) as? TextView
                titleTextView?.setTextColor(ContextCompat.getColor(this, android.R.color.white))
            }
        }
    }
    
    private fun setupAdapter() {
        paragraphAdapter = ArticleParagraphAdapter()
        binding.articleContent.adapter = paragraphAdapter
        
        // 优化RecyclerView设置
        binding.articleContent.itemAnimator = null
        binding.articleContent.setHasFixedSize(false) // 允许动态大小变化
        
        // 确保嵌套滚动正常工作
        binding.articleContent.isNestedScrollingEnabled = true
        binding.scrollView.isSmoothScrollingEnabled = true
    }
    
    private fun loadArticleData() {
        // 根据不同的文章ID加载不同的内容
        when (articleId) {
            "a1" -> loadNutritionArticle()
            "a2" -> loadBehaviorArticle()
            else -> loadDefaultArticle()
        }
    }
    
    private fun loadNutritionArticle() {
        // 加载封面图
        ImageLoaderUtils.loadCenterCropImage(binding.articleCoverImage, ImageUrls.Encyclopedia.CatNutrition.COVER)
        
        // 设置文章元数据
        binding.articleTag.text = "饮食"
        binding.articleDate.text = "2023-06-15"
        binding.articleTitle.text = "猫咪饮食指南：如何科学喂养你的爱猫"
        binding.viewCount.text = "2560次阅读"
        
        // 准备文章段落内容
        val paragraphs = mutableListOf<ArticleParagraph>()
        
        // 介绍段落
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "猫咪的饮食习惯直接影响着它们的健康状况和生活质量。作为铲屎官，了解如何正确地喂养你的爱猫至关重要。本文将详细介绍猫咪的营养需求，以及应该如何科学地选择猫粮和安排饮食计划。"
        ))
        
        // 小标题1
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.SUBTITLE,
            content = "猫咪的基本营养需求"
        ))
        
        // 段落1
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "猫咪是典型的肉食动物，它们需要摄入足够的蛋白质来维持健康。优质的蛋白质（如鸡肉、鱼类和牛肉）应该成为猫咪饮食的主要成分。此外，猫咪还需要特定的氨基酸，如牛磺酸和精氨酸，它们无法自行合成这些物质。"
        ))
        
        // 图片1
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.IMAGE,
            content = "健康的猫咪食物应该包含足够的蛋白质和脂肪酸",
            imageUrl = ImageUrls.Encyclopedia.CatNutrition.IMAGE1
        ))
        
        // 段落2
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "脂肪也是猫咪饮食中不可缺少的成分，它们提供能量并帮助吸收脂溶性维生素。特别是欧米茄-3和欧米茄-6脂肪酸，对猫咪的皮肤和毛发健康尤为重要。"
        ))
        
        // 小标题2
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.SUBTITLE,
            content = "如何选择合适的猫粮"
        ))
        
        // 段落3
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "市面上的猫粮种类繁多，如何选择最适合你爱猫的一款可能会让人感到困惑。以下是几个关键要点：\n\n" +
                    "1. 查看配料表：肉类蛋白应该排在首位，避免选择含有过多谷物填充物的产品。\n\n" +
                    "2. 考虑猫咪的年龄：幼猫、成年猫和老年猫有不同的营养需求。\n\n" +
                    "3. 特殊健康需求：如果你的猫咪有特定健康问题（如尿路问题、肥胖或食物过敏），可能需要特殊配方的猫粮。"
        ))
        
        // 图片2
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.IMAGE,
            content = "选择合适的猫粮时应注意配料表和营养成分",
            imageUrl = ImageUrls.Encyclopedia.CatNutrition.IMAGE2
        ))
        
        // 小标题3
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.SUBTITLE,
            content = "干粮与湿粮的选择"
        ))
        
        // 段落4
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "干粮和湿粮各有优缺点，理想的情况是两者结合使用：\n\n" +
                    "- 干粮：方便储存，有助于清洁牙齿，价格相对较低。但含水量低，可能导致猫咪摄入的水分不足。\n\n" +
                    "- 湿粮：含水量高，有助于猫咪补充水分，适口性好。但价格较高，开封后保存不便。"
        ))
        
        // 小标题4
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.SUBTITLE,
            content = "猫咪饮水的重要性"
        ))
        
        // 图片3
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.IMAGE,
            content = "确保猫咪随时有干净的饮用水",
            imageUrl = ImageUrls.Encyclopedia.CatNutrition.IMAGE3
        ))
        
        // 段落5
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "水对猫咪健康至关重要，尤其是对预防尿路问题有很大帮助。许多猫咪不喜欢静止的水，可以考虑购买猫咪饮水机，流动的水更能吸引它们喝水。"
        ))
        
        // 段落6
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "记住，每只猫咪都是独特的，有自己的偏好和需求。观察你的猫咪对不同食物的反应，并咨询兽医的建议，可以帮助你制定最适合的饮食计划。合理的饮食不仅能让猫咪保持健康，还能延长它们的寿命，让它们的生活更加幸福。"
        ))
        
        // 加载段落内容
        paragraphAdapter.submitList(paragraphs)
    }
    
    private fun loadBehaviorArticle() {
        // 加载封面图
        ImageLoaderUtils.loadCenterCropImage(binding.articleCoverImage, ImageUrls.Encyclopedia.CatBehavior.COVER)
        
        // 设置文章元数据
        binding.articleTag.text = "行为"
        binding.articleDate.text = "2023-06-10"
        binding.articleTitle.text = "猫咪行为解析：为什么猫咪会突然狂奔？"
        binding.viewCount.text = "1824次阅读"
        
        // 准备文章段落内容
        val paragraphs = mutableListOf<ArticleParagraph>()
        
        // 介绍段落
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "许多养猫的朋友可能都遇到过这样的情况：猫咪突然像被什么东西附身一样，疯狂地在家里跑来跑去，有时甚至上蹿下跳，几分钟后又恢复正常，仿佛什么都没发生过。这种行为被称为"+"猫咪狂奔"+"（也叫疯跑、夜间撒欢），是一种常见的猫咪行为。本文将从猫咪心理学角度解析这种行为背后的原因。"
        ))
        
        // 图片1
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.IMAGE,
            content = "猫咪在家中疯狂奔跑是一种本能行为",
            imageUrl = ImageUrls.Encyclopedia.CatBehavior.IMAGE1
        ))
        
        // 小标题1
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.SUBTITLE,
            content = "猫咪狂奔的常见原因"
        ))
        
        // 段落1
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "1. 释放过剩能量：尤其是室内猫，运动量不足时，它们会通过短时间的高强度活动来消耗积累的能量。\n\n" +
                    "2. 猎食本能：猫咪是天生的猎手，即使在家中没有猎物，它们的身体也会定期激活猎食行为。\n\n" +
                    "3. 排泄后的庆祝：许多猫咪在使用猫砂盆后会进行短暂的狂奔，这可能是一种原始的本能行为，旨在逃离可能暴露位置的气味。"
        ))
        
        // 小标题2
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.SUBTITLE,
            content = "何时需要关注"
        ))
        
        // 段落2
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "虽然猫咪狂奔通常是正常的，但在某些情况下可能表明存在问题：\n\n" +
                    "- 如果频率突然增加\n" +
                    "- 伴随着异常行为，如过度舔毛或攻击性行为\n" +
                    "- 似乎被某些无形的东西惊吓\n\n" +
                    "这些情况可能表明猫咪存在健康问题或压力，建议咨询兽医。"
        ))
        
        // 图片2
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.IMAGE,
            content = "观察猫咪的行为变化可以及时发现健康问题",
            imageUrl = ImageUrls.Encyclopedia.CatBehavior.IMAGE2
        ))
        
        // 小标题3
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.SUBTITLE,
            content = "如何应对猫咪狂奔"
        ))
        
        // 段落3
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "1. 提供足够的互动玩耍时间，帮助猫咪消耗能量。\n\n" +
                    "2. 设置猫咪攀爬和探索的空间，如猫树、猫爬架等。\n\n" +
                    "3. 使用智力玩具，如食物拼图或追逐玩具，刺激猫咪的大脑和身体。\n\n" +
                    "4. 保持规律的互动时间，帮助猫咪形成稳定的活动规律。"
        ))
        
        // 段落4
        paragraphs.add(ArticleParagraph(
            type = ParagraphType.TEXT,
            content = "总之，猫咪狂奔大多是健康、自然的行为表现，是它们释放能量和表达自我的方式。作为铲屎官，理解这些行为背后的原因，能够更好地满足猫咪的需求，与它们建立更深厚的情感联系。"
        ))
        
        // 加载段落内容
        paragraphAdapter.submitList(paragraphs)
    }
    
    private fun loadDefaultArticle() {
        // 加载封面图
        ImageLoaderUtils.loadCenterCropImage(binding.articleCoverImage, ImageUrls.Encyclopedia.CAT_NUTRITION)
        
        // 设置文章元数据
        binding.articleTag.text = "猫咪"
        binding.articleDate.text = "2023-06-01"
        binding.articleTitle.text = "默认文章"
        binding.viewCount.text = "1024次阅读"
        
        // 准备一个简单的默认内容
        val paragraphs = listOf(
            ArticleParagraph(
                type = ParagraphType.TEXT,
                content = "这是一篇默认文章，请选择其他文章阅读更多内容。"
            )
        )
        
        // 加载段落内容
        paragraphAdapter.submitList(paragraphs)
    }
    
    private fun setupBottomActions() {
        // 喜欢按钮状态
        var isLiked = false
        
        binding.btnLike.setOnClickListener {
            // 切换喜欢状态
            isLiked = !isLiked
            
            // 更新图标选中状态
            binding.likeIcon.isSelected = isLiked
            
            // 更新文字颜色
            if (isLiked) {
                binding.likeText.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light))
                Toast.makeText(this, "已添加喜欢", Toast.LENGTH_SHORT).show()
            } else {
                binding.likeText.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
                Toast.makeText(this, "已取消喜欢", Toast.LENGTH_SHORT).show()
            }
        }
        
        binding.btnComment.setOnClickListener {
            Toast.makeText(this, "评论功能开发中", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnShare.setOnClickListener {
            Toast.makeText(this, "分享功能开发中", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnFavorite.setOnClickListener {
            Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show()
        }
    }
} 