package com.qh.wypet.ui.encyclopedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.databinding.ItemEncyclopediaArticleBinding
import com.qh.wypet.utils.ImageLoaderUtils

class ArticleAdapter(
    private val onArticleClick: (Article) -> Unit
) : ListAdapter<Article, ArticleAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEncyclopediaArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ViewHolder(private val binding: ItemEncyclopediaArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onArticleClick(getItem(position))
                }
            }
        }

        fun bind(article: Article) {
            binding.articleTitle.text = article.title
            binding.articleDescription.text = article.description
            
            // 使用ImageLoaderUtils加载图片
            ImageLoaderUtils.loadCenterCropImage(binding.articleThumbnail, article.imageUrl)
            
            binding.articleTag.text = article.tag
            binding.articleTimestamp.text = article.publishDate
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
} 