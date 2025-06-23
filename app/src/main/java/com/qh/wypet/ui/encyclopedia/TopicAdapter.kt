package com.qh.wypet.ui.encyclopedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.databinding.ItemEncyclopediaTopicBinding
import com.qh.wypet.utils.ImageLoaderUtils

class TopicAdapter(
    private val onTopicClick: (Topic) -> Unit
) : ListAdapter<Topic, TopicAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEncyclopediaTopicBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val topic = getItem(position)
        holder.bind(topic)
    }

    inner class ViewHolder(private val binding: ItemEncyclopediaTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onTopicClick(getItem(position))
                }
            }
        }

        fun bind(topic: Topic) {
            binding.topicTitle.text = topic.title
            binding.topicDescription.text = topic.description
            binding.topicArticleCount.text = "${topic.articleCount}篇文章"
            
            // 使用ImageLoaderUtils加载图片
            ImageLoaderUtils.loadCenterCropImage(binding.topicImage, topic.imageUrl)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Topic>() {
        override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
            return oldItem == newItem
        }
    }
} 