package com.qh.wypet.ui.aiqa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R

class AiQaChatAdapter(private val messages: List<AiMessage>) : 
    RecyclerView.Adapter<AiQaChatAdapter.MessageViewHolder>() {
    
    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_AI = 2
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_USER) {
            val view = layoutInflater.inflate(R.layout.item_message_user, parent, false)
            MessageViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.item_message_ai, parent, false)
            MessageViewHolder(view)
        }
    }
    
    override fun getItemCount(): Int = messages.size
    
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.content
    }
    
    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_AI
    }
    
    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.messageText)
    }
} 