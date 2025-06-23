package com.qh.wypet.ui.social

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.qh.wypet.R
import com.qh.wypet.databinding.ActivitySocialFeedDetailBinding
import com.qh.wypet.utils.PetPhotographyImages

class SocialFeedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySocialFeedDetailBinding
    private lateinit var imageAdapter: SocialFeedDetailImageAdapter
    private lateinit var commentAdapter: SocialCommentAdapter
    
    private var feedId: String? = null
    private var username: String? = null
    private var avatarUrl: String? = null
    private var content: String? = null
    private var imageUrl: String? = null
    private var likes: Int = 0
    private var comments: Int = 0
    private val commentsList = mutableListOf<SocialCommentModel>()
    
    private var currentReplyTo: SocialCommentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable content transitions
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        super.onCreate(savedInstanceState)
        
        // Enable shared element transitions
        supportPostponeEnterTransition()
        
        binding = ActivitySocialFeedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        feedId = intent.getStringExtra(EXTRA_FEED_ID)
        username = intent.getStringExtra(EXTRA_USERNAME)
        avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        content = intent.getStringExtra(EXTRA_CONTENT)
        imageUrl = intent.getStringExtra(EXTRA_IMAGE_URL)
        likes = intent.getIntExtra(EXTRA_LIKES, 0)
        comments = intent.getIntExtra(EXTRA_COMMENTS, 0)

        setupToolbar()
        setupImageViewPager()
        setupPageIndicator()
        fillContent()
        setupListeners()
        setupMockComments()
        setupCommentsRecyclerView()
        setupCommentInput()
    }

    private fun setupToolbar() {
        binding.backButton.setOnClickListener {
            finish()
        }
        
        // Set username and avatar
        binding.usernameTextView.text = username ?: "宠物达人"
        
        Glide.with(this)
            .load(avatarUrl)
            .placeholder(R.drawable.ic_pet)
            .circleCrop()
            .into(binding.avatarImageView)
            
        binding.followButton.setOnClickListener {
            Toast.makeText(this, "已关注 $username", Toast.LENGTH_SHORT).show()
//            binding.followButton.text = "已关注"
//            binding.followButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
        
        binding.moreOptionsButton.setOnClickListener {
            Toast.makeText(this, "更多选项", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupImageViewPager() {
        // Create a list of images for the ViewPager
        // For demo purposes, we'll use multiple copies of the same image
        // In real app, you'd have multiple images per post
        val images = listOf(
            imageUrl ?: PetPhotographyImages.getRandomImageUrl(),
            PetPhotographyImages.getRandomImageUrl(),
            PetPhotographyImages.getRandomImageUrl(),
            PetPhotographyImages.getRandomImageUrl()
        )
        
        imageAdapter = SocialFeedDetailImageAdapter(images)
        binding.imageViewPager.adapter = imageAdapter
        
        // Update page indicator when page changes
        binding.imageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updatePageIndicator(position, images.size)
            }
        })
    }
    
    private fun setupPageIndicator() {
        // Clear any existing indicators
        binding.pageIndicator.removeAllViews()
        
        // Create dots for page indicator
        val imageCount = 4 // Assuming 4 images
        for (i in 0 until imageCount) {
            val dot = View(this)
            val size = resources.getDimensionPixelSize(R.dimen.dot_size)
            val margin = resources.getDimensionPixelSize(R.dimen.dot_margin)
            val params = LinearLayout.LayoutParams(size, size)
            params.setMargins(margin, 0, margin, 0)
            dot.layoutParams = params
            
            if (i == 0) {
                dot.setBackgroundResource(R.drawable.tab_indicator_selected)
            } else {
                dot.setBackgroundResource(R.drawable.tab_indicator_default)
            }
            
            binding.pageIndicator.addView(dot)
        }
    }
    
    private fun updatePageIndicator(currentPage: Int, totalPages: Int) {
        for (i in 0 until binding.pageIndicator.childCount) {
            val dot = binding.pageIndicator.getChildAt(i)
            if (i == currentPage) {
                dot.setBackgroundResource(R.drawable.tab_indicator_selected)
            } else {
                dot.setBackgroundResource(R.drawable.tab_indicator_default)
            }
        }
        
        // Also update the text indicator in the current page
        val viewHolder = binding.imageViewPager.getChildAt(0)?.findViewById<TextView>(R.id.pageIndicatorText)
        viewHolder?.text = "${currentPage + 1}/$totalPages"
    }
    
    private fun fillContent() {
        // Set content
        binding.contentTextView.text = content
    }
    
    private fun setupListeners() {
        // 查看全部评论按钮
        binding.viewAllComments.setOnClickListener {
            Toast.makeText(this, "查看全部评论", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupMockComments() {
        // 创建带回复的虚拟评论
        val comment1 = SocialCommentModel(
            id = "1",
            userId = "user1",
            userName = "宠无忧用户8275", 
            userAvatar = "https://randomuser.me/api/portraits/men/2.jpg",
            content = "博主家的蓝猫好可爱，请问是在宠无忧上购买的猫粮吗？效果看起来很好！",
            date = "6月4日",
            likes = 3,
            isLiked = false,
            location = "广东省",
            replies = listOf(
                SocialCommentModel(
                    id = "1.1",
                    userId = "author",
                    userName = "蓝猫爱好者", 
                    userAvatar = avatarUrl ?: "https://randomuser.me/api/portraits/women/1.jpg",
                    content = "是的，宠无忧上的皇家S33猫粮，我家蓝猫吃了两个月，毛色确实变好了",
                    date = "6月5日",
                    likes = 1,
                    isLiked = false,
                    location = "上海市",
                    replyTo = "1",
                    replyToUsername = "宠无忧用户8275"
                ),
                SocialCommentModel(
                    id = "1.2",
                    userId = "user1",
                    userName = "宠无忧用户8275",
                    userAvatar = "https://randomuser.me/api/portraits/men/2.jpg",
                    content = "谢谢推荐！我也去宠无忧上买一包试试，希望对我家猫咪也有效果",
                    date = "6月5日",
                    likes = 1,
                    isLiked = false,
                    location = "广东省",
                    replyTo = "1.1",
                    replyToUsername = "蓝猫爱好者"
                )
            )
        )
        
        val comment2 = SocialCommentModel(
            id = "2",
            userId = "user3",
            userName = "猫咪医生小王",
            userAvatar = "https://randomuser.me/api/portraits/women/3.jpg",
            content = "作为宠物医生，我想补充一点：英短蓝猫容易有泪痕问题，建议使用宠无忧APP上的猫咪专用湿巾定期清理，避免发炎。另外定期体检很重要，可以在宠无忧上预约上门服务。",
            date = "6月13日",
            likes = 25,
            isLiked = false,
            location = "广东省",
            replies = listOf(
                SocialCommentModel(
                    id = "2.1",
                    userId = "author",
                    userName = "蓝猫爱好者",
                    userAvatar = avatarUrl ?: "https://randomuser.me/api/portraits/women/1.jpg",
                    content = "非常感谢专业建议！我已经在宠无忧上预约了下周的体检服务，上门检查真的很方便，不用担心把猫咪带出门的应激反应。",
                    date = "6月13日",
                    likes = 18,
                    isLiked = false,
                    location = "湖北省",
                    replyTo = "2",
                    replyToUsername = "猫咪医生小王"
                )
            )
        )
        
        val comment3 = SocialCommentModel(
            id = "3",
            userId = "user4",
            userName = "宠物达人大壮",
            userAvatar = "https://randomuser.me/api/portraits/men/4.jpg",
            content = "刚用宠无忧给我家猫咪预约了洗澡服务，技师很专业，带了专门猫咪用的沐浴露，而且价格比实体店便宜！强烈推荐大家使用宠无忧APP~",
            date = "3天前",
            likes = 42,
            isLiked = false,
            location = "辽宁省"
        )
        
        commentsList.addAll(listOf(comment1, comment2, comment3))
        
        // 更新评论数量显示
        binding.commentCountTextView.text = "(${commentsList.size})"
    }
    
    private fun setupCommentsRecyclerView() {
        commentAdapter = SocialCommentAdapter(
            comments = commentsList,
            onLikeClickListener = { comment ->
                Toast.makeText(this, "点赞评论: ${comment.userName}", Toast.LENGTH_SHORT).show()
            },
            onReplyClickListener = { comment ->
                // 设置回复对象并聚焦输入框
                setReplyTarget(comment)
            },
            onExpandRepliesClickListener = { parentComment ->
                Toast.makeText(this, "查看全部回复", Toast.LENGTH_SHORT).show()
            }
        )
        
        binding.commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SocialFeedDetailActivity)
            adapter = commentAdapter
        }
    }
    
    private fun setupCommentInput() {
        // 初始时显示默认提示
        resetCommentInput()
        
        // 点击输入框弹出键盘
        binding.commentInputContainer.setOnClickListener {
            binding.commentEditText.requestFocus()
            showKeyboard(binding.commentEditText)
        }
        
        // 底部点赞按钮
        binding.likeButtonBottom.setOnClickListener {
            Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show()
        }
        
        // 底部收藏按钮
        binding.favoriteButtonBottom.setOnClickListener {
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show()
        }
        
        // 底部评论按钮
        binding.commentButtonBottom.setOnClickListener {
            binding.commentEditText.requestFocus()
            showKeyboard(binding.commentEditText)
        }
        
        // 评论输入框回车键监听
        binding.commentEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
                val commentText = binding.commentEditText.text.toString().trim()
                if (commentText.isNotEmpty()) {
                    addNewComment(commentText)
                    binding.commentEditText.setText("")
                    hideKeyboard(binding.commentEditText)
                    resetCommentInput()
                }
                true
            } else {
                false
            }
        }
    }
    
    private fun addNewComment(commentText: String) {
        val currentTime = "刚刚"
        
        if (currentReplyTo == null) {
            // 添加新的主评论
            val newComment = SocialCommentModel(
                id = System.currentTimeMillis().toString(),
                userId = "currentUser",
                userName = "当前用户",
                userAvatar = "https://randomuser.me/api/portraits/women/5.jpg",
                content = commentText,
                date = currentTime,
                likes = 0,
                location = "上海市"
            )
            
            commentsList.add(0, newComment)
        } else {
            // 添加回复评论
            // 找到要回复的评论的父评论
            val parentCommentId = if (currentReplyTo?.replyTo != null) {
                // 如果回复的是回复，找到原始的主评论ID
                commentsList.find { it.id == currentReplyTo?.replyTo || it.replies.any { reply -> reply.id == currentReplyTo?.replyTo } }?.id
            } else {
                // 如果回复的是主评论，使用它的ID
                currentReplyTo?.id
            }
            
            val newReply = SocialCommentModel(
                id = System.currentTimeMillis().toString(),
                userId = "currentUser",
                userName = "当前用户",
                userAvatar = "https://randomuser.me/api/portraits/women/5.jpg",
                content = commentText,
                date = currentTime,
                likes = 0,
                location = "上海市",
                replyTo = currentReplyTo?.id,
                replyToUsername = currentReplyTo?.userName
            )
            
            // 找到父评论并添加这个回复
            val parentComment = commentsList.find { it.id == parentCommentId }
            if (parentComment != null) {
                val updatedReplies = parentComment.replies.toMutableList()
                updatedReplies.add(newReply)
                
                // 用更新后的评论替换原评论
                val parentIndex = commentsList.indexOf(parentComment)
                commentsList[parentIndex] = parentComment.copy(replies = updatedReplies)
            }
        }
        
        // 更新评论数量显示
        val totalCount = getTotalCommentsCount()
        binding.commentCountTextView.text = "($totalCount)"

        // 通知适配器更新
        commentAdapter.notifyDataSetChanged()
    }
    
    private fun getTotalCommentsCount(): Int {
        var count = commentsList.size
        commentsList.forEach { comment ->
            count += comment.replies.size
        }
        return count
    }
    
    private fun setReplyTarget(comment: SocialCommentModel) {
        currentReplyTo = comment
        binding.commentEditText.hint = "回复 @${comment.userName}..."
        binding.commentEditText.requestFocus()
        showKeyboard(binding.commentEditText)
    }
    
    private fun resetCommentInput() {
        currentReplyTo = null
        binding.commentEditText.hint = "说点什么..."
    }
    
    private fun showKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
    
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private const val EXTRA_FEED_ID = "extra_feed_id"
        private const val EXTRA_USERNAME = "extra_username"
        private const val EXTRA_AVATAR_URL = "extra_avatar_url"
        private const val EXTRA_CONTENT = "extra_content"
        private const val EXTRA_IMAGE_URL = "extra_image_url"
        private const val EXTRA_LIKES = "extra_likes"
        private const val EXTRA_COMMENTS = "extra_comments"
        
        fun newIntent(context: Context, feedItem: SocialFeedItem): Intent {
            return Intent(context, SocialFeedDetailActivity::class.java).apply {
                putExtra(EXTRA_FEED_ID, feedItem.id)
                putExtra(EXTRA_USERNAME, feedItem.username)
                putExtra(EXTRA_AVATAR_URL, feedItem.avatarUrl)
                putExtra(EXTRA_CONTENT, feedItem.content)
                putExtra(EXTRA_IMAGE_URL, feedItem.imageUrl)
                putExtra(EXTRA_LIKES, feedItem.likes)
                putExtra(EXTRA_COMMENTS, feedItem.comments)
            }
        }
    }
} 