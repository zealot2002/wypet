package com.qh.wypet.ui.encyclopedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.databinding.ItemQuestionBinding

class QuestionAdapter(
    private val onQuestionClick: (Question) -> Unit
) : ListAdapter<Question, QuestionAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = getItem(position)
        holder.bind(question)
    }

    inner class ViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onQuestionClick(getItem(position))
                }
            }
        }

        fun bind(question: Question) {
            binding.questionTitle.text = question.title
            binding.questionPreview.text = question.content
            
            // Show bookmark icon if question is bookmarked
            binding.bookmarkIcon.visibility = if (question.isBookmarked) View.VISIBLE else View.INVISIBLE
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }
} 