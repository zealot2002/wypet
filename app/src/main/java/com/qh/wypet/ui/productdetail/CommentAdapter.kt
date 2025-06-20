package com.qh.wypet.ui.productdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.qh.wypet.R

class CommentAdapter(private val comments: List<CommentModel>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size.coerceAtMost(3) // Limit to 3 comments

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarView: ImageView = itemView.findViewById(R.id.userAvatar)
        private val nameView: TextView = itemView.findViewById(R.id.userName)
        private val dateView: TextView = itemView.findViewById(R.id.commentDate)
        private val contentView: TextView = itemView.findViewById(R.id.commentContent)

        fun bind(comment: CommentModel) {
            nameView.text = comment.userName
            dateView.text = comment.date
            contentView.text = comment.content

            Glide.with(itemView.context)
                .load(comment.userAvatar)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.shape_circle)
                .into(avatarView)
        }
    }
} 