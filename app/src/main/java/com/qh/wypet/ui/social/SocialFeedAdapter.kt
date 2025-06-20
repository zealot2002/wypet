package com.qh.wypet.ui.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qh.wypet.R
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
    }

    override fun getItemCount(): Int = items.size

    inner class SocialFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        private val likesCountTextView: TextView = itemView.findViewById(R.id.likesCountTextView)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val contentImageView: ImageView = itemView.findViewById(R.id.contentImageView)

        fun bind(item: SocialFeedItem) {
            usernameTextView.text = item.username
            contentTextView.text = item.content
            likesCountTextView.text = item.likes.toString()
            
            // Load avatar image
            val avatarUrl = item.avatarUrl ?: getRandomAvatarUrl()
            Glide.with(itemView.context)
                .load(avatarUrl)
                .placeholder(R.drawable.ic_pet)
                .circleCrop()
                .into(avatarImageView)
            
            // Load content image - use PetPhotographyImages for content images
            val imageUrl = item.imageUrl ?: getRandomContentImage()
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_pet)
                .into(contentImageView)
            
            // Set click listeners
            itemView.setOnClickListener {
                // Launch detail activity
                val context = itemView.context
                val intent = SocialFeedDetailActivity.newIntent(context, item)
                context.startActivity(intent)
                
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