package com.qh.wypet.ui.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R

class SocialFeedAdapter(
    private val items: List<SocialFeedItem>,
    private val listener: SocialFeedInteractionListener? = null
) : RecyclerView.Adapter<SocialFeedAdapter.SocialFeedViewHolder>() {

    interface SocialFeedInteractionListener {
        fun onItemClicked(item: SocialFeedItem)
        fun onLikeClicked(item: SocialFeedItem)
        fun onCommentClicked(item: SocialFeedItem)
        fun onShareClicked(item: SocialFeedItem)
        fun onFavoriteClicked(item: SocialFeedItem)
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
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        private val likesCountTextView: TextView = itemView.findViewById(R.id.likesCountTextView)
        private val commentsCountTextView: TextView = itemView.findViewById(R.id.commentsCountTextView)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val contentImageView: ImageView = itemView.findViewById(R.id.contentImageView)
        private val favoriteImageView: ImageView = itemView.findViewById(R.id.favoriteImageView)
        private val likeImageView: ImageView = itemView.findViewById(R.id.likeImageView)
        private val commentImageView: ImageView = itemView.findViewById(R.id.commentImageView)
        private val shareImageView: ImageView = itemView.findViewById(R.id.shareImageView)
        
        fun bind(item: SocialFeedItem) {
            usernameTextView.text = item.username
            timeTextView.text = item.timeAgo
            contentTextView.text = item.content
            likesCountTextView.text = item.likes.toString()
            commentsCountTextView.text = item.comments.toString()
            
            // Set avatar placeholder for now
            // In real app, load image with Glide/Coil
            avatarImageView.setImageResource(R.drawable.ic_pet)
            
            // Set our custom icons
            likeImageView.setImageResource(R.drawable.ic_like)
            commentImageView.setImageResource(R.drawable.ic_comment)
            shareImageView.setImageResource(R.drawable.ic_share)
            
            // Handle image visibility
            if (item.imageUrl != null) {
                contentImageView.visibility = View.VISIBLE
                // In real app, load image with Glide/Coil
                contentImageView.setImageResource(R.drawable.ic_pet)
            } else {
                contentImageView.visibility = View.GONE
            }
            
            // Set favorite state
            favoriteImageView.setImageResource(R.drawable.ic_favorite)
            favoriteImageView.alpha = if (item.isFavorite) 1.0f else 0.5f
            
            // Set click listeners
            itemView.setOnClickListener {
                listener?.onItemClicked(item)
            }
            
            favoriteImageView.setOnClickListener {
                item.isFavorite = !item.isFavorite
                favoriteImageView.alpha = if (item.isFavorite) 1.0f else 0.5f
                listener?.onFavoriteClicked(item)
            }
            
            likeImageView.setOnClickListener {
                listener?.onLikeClicked(item)
            }
            
            commentImageView.setOnClickListener {
                listener?.onCommentClicked(item)
            }
            
            shareImageView.setOnClickListener {
                listener?.onShareClicked(item)
            }
        }
    }
} 