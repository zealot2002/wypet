package com.qh.wypet.ui.social

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.qh.wypet.R
import com.qh.wypet.databinding.ActivitySocialFeedDetailBinding
import com.qh.wypet.utils.PetPhotographyImages

class SocialFeedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySocialFeedDetailBinding
    private lateinit var imageAdapter: SocialFeedDetailImageAdapter
    
    private var feedId: String? = null
    private var username: String? = null
    private var avatarUrl: String? = null
    private var content: String? = null
    private var imageUrl: String? = null
    private var likes: Int = 0
    private var comments: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        
        // Set counters
        binding.likesCountTextView.text = likes.toString()
        binding.commentsCountTextView.text = comments.toString()
        binding.favoritesCountTextView.text = "1"  // Default value
    }
    
    private fun setupListeners() {
        binding.likeButton.setOnClickListener {
            likes++
            binding.likesCountTextView.text = likes.toString()
            Toast.makeText(this, "点赞成功", Toast.LENGTH_SHORT).show()
        }
        
        binding.favoriteButton.setOnClickListener {
            val count = binding.favoritesCountTextView.text.toString().toInt() + 1
            binding.favoritesCountTextView.text = count.toString()
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show()
        }
        
        binding.commentButton.setOnClickListener {
            Toast.makeText(this, "查看评论", Toast.LENGTH_SHORT).show()
        }
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