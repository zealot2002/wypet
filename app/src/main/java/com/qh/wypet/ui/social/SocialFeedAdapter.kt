package com.qh.wypet.ui.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.qh.wypet.R
import com.qh.wypet.utils.ImageLoaderUtils
import com.qh.wypet.utils.ImageUrls
import com.qh.wypet.utils.PetPhotographyImages

class SocialFeedAdapter(
    private val items: List<SocialFeedItem>,
    private val listener: SocialFeedInteractionListener? = null
) : RecyclerView.Adapter<SocialFeedAdapter.SocialFeedViewHolder>() {

    interface SocialFeedInteractionListener {
        fun onItemClicked(item: SocialFeedItem)
        fun onLikeClicked(item: SocialFeedItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialFeedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_social_feed, parent, false)
        return SocialFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: SocialFeedViewHolder, position: Int) {
        holder.bind(items[position])
        
        // 根据文本内容长度设置不同的图片高宽比
        val item = items[position]
        val contentLength = item.content.length
        
        // 调整图片的高宽比，使内容少的卡片更小，内容多的卡片更大
        val imageParams = holder.contentImageView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
        
        // 保持宽度不变，根据文本长度修改高宽比
        if (contentLength < 20) { // 短文本
            imageParams.dimensionRatio = "2:2" // 降低高度
        } else if (contentLength > 100) { // 长文本 
            imageParams.dimensionRatio = "2:3.5" // 增加高度
        } else { // 中等长度
            imageParams.dimensionRatio = "2:3" // 默认高宽比
        }
        
        holder.contentImageView.layoutParams = imageParams
    }

    override fun getItemCount(): Int = items.size

    inner class SocialFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        private val likesCountTextView: TextView = itemView.findViewById(R.id.likesCountTextView)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val contentImageView: ImageView = itemView.findViewById(R.id.contentImageView)

        fun bind(item: SocialFeedItem) {
            usernameTextView.text = item.username
            contentTextView.text = item.content
            likesCountTextView.text = item.likes.toString()
            
            // Load avatar image
            val avatarUrl = item.avatarUrl ?: getRandomAvatarUrl()
            ImageLoaderUtils.loadCircleImage(avatarImageView, avatarUrl)
            
            // Load content image - use PetPhotographyImages for content images
            val imageUrl = item.imageUrl ?: getRandomContentImage()
            ImageLoaderUtils.loadCenterCropImage(contentImageView, imageUrl)
            
            // Set click listeners
            itemView.setOnClickListener {
                // Create shared element transition
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as androidx.appcompat.app.AppCompatActivity,
                    Pair(contentImageView, "social_feed_image")
                )
                
                // Launch detail activity with transition
                val context = itemView.context
                val intent = SocialFeedDetailActivity.newIntent(context, item)
                context.startActivity(intent, options.toBundle())
                
                // Also notify the listener
                listener?.onItemClicked(item)
            }
            
//            likeImageView.setOnClickListener {
//                listener?.onLikeClicked(item)
//            }
        }
        
        private fun getRandomAvatarUrl(): String {
            val avatars = listOf(
                ImageUrls.USER_AVATAR_1,
                ImageUrls.USER_AVATAR_2,
                ImageUrls.USER_AVATAR_3
            )
            return avatars.random()
        }
        
        private fun getRandomContentImage(): String {
            // Use PetPhotographyImages instead of ImageUrls
            return PetPhotographyImages.getRandomImageUrl()
        }
    }
} 