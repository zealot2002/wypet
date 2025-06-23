package com.qh.wypet.ui.social

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R
import com.qh.wypet.utils.ImageLoaderUtils

class SocialCommentAdapter(
    private val comments: List<SocialCommentModel>,
    private val onLikeClickListener: ((SocialCommentModel) -> Unit)? = null,
    private val onReplyClickListener: ((SocialCommentModel) -> Unit)? = null,
    private val onExpandRepliesClickListener: ((SocialCommentModel) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_COMMENT = 0
        private const val TYPE_REPLY = 1
        private const val MAX_VISIBLE_REPLIES = 2 // 默认显示的回复数量
    }

    override fun getItemViewType(position: Int): Int {
        // 遍历查找实际位置对应的是主评论还是回复
        var currentPosition = 0
        for (comment in comments) {
            // 如果是主评论位置
            if (currentPosition == position) {
                return TYPE_COMMENT
            }
            currentPosition++
            
            // 如果是回复位置
            val visibleReplies = if (comment.replies.size > MAX_VISIBLE_REPLIES) 
                MAX_VISIBLE_REPLIES else comment.replies.size
            
            for (i in 0 until visibleReplies) {
                if (currentPosition == position) {
                    return TYPE_REPLY
                }
                currentPosition++
            }
            
            // "展开更多回复"按钮位置
            if (comment.replies.size > MAX_VISIBLE_REPLIES) {
                if (currentPosition == position) {
                    return TYPE_REPLY // 使用相同的视图类型，但会特别处理
                }
                currentPosition++
            }
        }
        return TYPE_COMMENT // 默认
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_COMMENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_comment, parent, false)
                CommentViewHolder(view)
            }
            TYPE_REPLY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_comment, parent, false)
                ReplyViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val (comment, isReply, isExpandButton, parentComment) = getCommentAtPosition(position)
        
        when (holder) {
            is CommentViewHolder -> holder.bind(comment)
            is ReplyViewHolder -> {
                if (isExpandButton) {
                    // 如果是"展开更多回复"按钮
                    holder.bindExpandButton(parentComment!!)
                } else {
                    // 正常的回复评论
                    holder.bind(comment, parentComment)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        for (comment in comments) {
            // 主评论
            count++
            
            // 回复
            val visibleReplies = if (comment.replies.size > MAX_VISIBLE_REPLIES) 
                MAX_VISIBLE_REPLIES else comment.replies.size
            count += visibleReplies
            
            // "展开更多回复"按钮
            if (comment.replies.size > MAX_VISIBLE_REPLIES) {
                count++
            }
        }
        return count
    }
    
    // 根据position获取对应的评论对象和类型
    private fun getCommentAtPosition(position: Int): CommentItem {
        var currentPosition = 0
        
        for (comment in comments) {
            // 主评论
            if (currentPosition == position) {
                return CommentItem(comment, false, false, null)
            }
            currentPosition++
            
            // 可见的回复
            val visibleReplies = if (comment.replies.size > MAX_VISIBLE_REPLIES) 
                MAX_VISIBLE_REPLIES else comment.replies.size
            
            for (i in 0 until visibleReplies) {
                if (currentPosition == position) {
                    return CommentItem(comment.replies[i], true, false, comment)
                }
                currentPosition++
            }
            
            // "展开更多回复"按钮
            if (comment.replies.size > MAX_VISIBLE_REPLIES) {
                if (currentPosition == position) {
                    // 使用第一条不可见的回复作为占位
                    return CommentItem(comment.replies[MAX_VISIBLE_REPLIES], true, true, comment)
                }
                currentPosition++
            }
        }
        
        throw IndexOutOfBoundsException("Position $position is out of bounds")
    }
    
    // 数据类用于表示在特定位置的评论信息
    data class CommentItem(
        val comment: SocialCommentModel,
        val isReply: Boolean,
        val isExpandButton: Boolean,
        val parentComment: SocialCommentModel?
    )

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarView: ImageView = itemView.findViewById(R.id.userAvatar)
        private val nameView: TextView = itemView.findViewById(R.id.userName)
        private val dateView: TextView = itemView.findViewById(R.id.commentDate)
        private val contentView: TextView = itemView.findViewById(R.id.commentContent)
        private val replyButton: TextView by lazy {
            TextView(itemView.context).apply {
                text = "回复"
                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                textSize = 12f
                setPadding(8, 8, 8, 8)
            }
        }

        init {
            // 在日期后面添加回复按钮
            val container = itemView.findViewById<LinearLayout>(R.id.commentHeaderContainer)
            container?.addView(replyButton)
        }

        fun bind(comment: SocialCommentModel) {
            nameView.text = comment.userName
            
            // 显示地点和日期
            val locationText = if (comment.location != null) "${comment.location} · " else ""
            dateView.text = "$locationText${comment.date}"
            
            contentView.text = comment.content

            // 使用统一的图片加载工具
            ImageLoaderUtils.loadCircleImage(avatarView, comment.userAvatar)
                
            // 设置回复按钮点击事件
            replyButton.setOnClickListener {
                onReplyClickListener?.invoke(comment)
            }
            
            // 设置点赞点击事件
            itemView.setOnClickListener {
                onLikeClickListener?.invoke(comment)
            }
        }
    }

    inner class ReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarView: ImageView = itemView.findViewById(R.id.userAvatar)
        private val nameView: TextView = itemView.findViewById(R.id.userName)
        private val dateView: TextView = itemView.findViewById(R.id.commentDate)
        private val contentView: TextView = itemView.findViewById(R.id.commentContent)
        private val replyButton: TextView by lazy {
            TextView(itemView.context).apply {
                text = "回复"
                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
                textSize = 12f
                setPadding(8, 8, 8, 8)
            }
        }
        
        init {
            // 在日期后面添加回复按钮
            val container = itemView.findViewById<LinearLayout>(R.id.commentHeaderContainer)
            container?.addView(replyButton)
            
            // 设置回复评论的缩进
            val params = itemView.layoutParams as? ViewGroup.MarginLayoutParams
            params?.marginStart = itemView.resources.getDimensionPixelSize(R.dimen.reply_indent)
            itemView.layoutParams = params
        }

        fun bind(comment: SocialCommentModel, parentComment: SocialCommentModel?) {
            nameView.text = comment.userName
            
            // 显示地点和日期
            val locationText = if (comment.location != null) "${comment.location} · " else ""
            dateView.text = "$locationText${comment.date}"
            
            // 如果是回复某人的评论，显示"回复@xxx："
            val replyPrefix = if (comment.replyToUsername != null) {
                "回复 @${comment.replyToUsername}："
            } else ""
            contentView.text = "$replyPrefix${comment.content}"

            // 使用统一的图片加载工具
            ImageLoaderUtils.loadCircleImage(avatarView, comment.userAvatar)
                
            // 设置回复按钮点击事件
            replyButton.setOnClickListener {
                onReplyClickListener?.invoke(comment)
            }
            
            // 设置点赞点击事件
            itemView.setOnClickListener {
                onLikeClickListener?.invoke(comment)
            }
        }
        
        fun bindExpandButton(parentComment: SocialCommentModel) {
            // 隐藏常规评论UI
            avatarView.visibility = View.GONE
            nameView.visibility = View.GONE
            dateView.visibility = View.GONE
            replyButton.visibility = View.GONE
            
            // 显示"展开更多回复"文本
            val remainingReplies = parentComment.replies.size - MAX_VISIBLE_REPLIES
            contentView.text = "展开${remainingReplies}条回复 >"
            contentView.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
            
            // 点击展开更多回复
            itemView.setOnClickListener {
                onExpandRepliesClickListener?.invoke(parentComment)
            }
        }
    }
} 