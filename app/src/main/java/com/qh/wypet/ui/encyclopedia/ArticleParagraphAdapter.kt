package com.qh.wypet.ui.encyclopedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.databinding.ItemArticleParagraphBinding
import com.qh.wypet.utils.ImageLoaderUtils

/**
 * 文章段落适配器
 */
class ArticleParagraphAdapter :
    ListAdapter<ArticleParagraph, ArticleParagraphAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleParagraphBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        
        // 为调试目的，给每个项添加一个边框
        holder.itemView.setBackgroundResource(android.R.color.transparent)
    }

    class ViewHolder(private val binding: ItemArticleParagraphBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(paragraph: ArticleParagraph) {
            // 先重置所有视图的可见性
            binding.paragraphText.visibility = View.GONE
            binding.imageContainer.visibility = View.GONE
            binding.paragraphSubtitle.visibility = View.GONE

            // 根据段落类型显示相应的视图
            when (paragraph.type) {
                ParagraphType.TEXT -> {
                    binding.paragraphText.visibility = View.VISIBLE
                    binding.paragraphText.text = paragraph.content
                }
                ParagraphType.IMAGE -> {
                    binding.imageContainer.visibility = View.VISIBLE
                    binding.imageCaption.text = paragraph.content
                    paragraph.imageUrl?.let { url ->
                        ImageLoaderUtils.loadFitCenterImage(binding.paragraphImage, url)
                    }
                }
                ParagraphType.SUBTITLE -> {
                    binding.paragraphSubtitle.visibility = View.VISIBLE
                    binding.paragraphSubtitle.text = paragraph.content
                }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ArticleParagraph>() {
        override fun areItemsTheSame(oldItem: ArticleParagraph, newItem: ArticleParagraph): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArticleParagraph, newItem: ArticleParagraph): Boolean {
            return oldItem == newItem
        }
    }
} 