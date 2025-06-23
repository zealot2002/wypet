package com.qh.wypet.ui.aiqa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.qh.wypet.R
import io.noties.markwon.Markwon
import java.util.concurrent.CopyOnWriteArrayList

class AiQaChatAdapter(private val messages: MutableList<AiMessage>) : 
    RecyclerView.Adapter<AiQaChatAdapter.MessageViewHolder>() {
    
    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_AI = 2
    }
    
    // 创建Markwon实例，用于渲染Markdown
    private var markwon: Markwon? = null
    
    // 使用线程安全的列表来避免并发修改异常
    private val safeMessages = CopyOnWriteArrayList<AiMessage>().apply {
        addAll(messages)
    }
    
    init {
        // 设置稳定ID
        setHasStableIds(true)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // 懒初始化Markwon
        if (markwon == null) {
            markwon = Markwon.builder(parent.context).build()
        }
        
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_USER) {
            val view = layoutInflater.inflate(R.layout.item_message_user, parent, false)
            MessageViewHolder(view)
        } else {
            val view = layoutInflater.inflate(R.layout.item_message_ai, parent, false)
            MessageViewHolder(view)
        }
    }
    
    override fun getItemCount(): Int = safeMessages.size
    
    // 提供稳定的ID，避免RecyclerView位置错误
    override fun getItemId(position: Int): Long {
        if (position < 0 || position >= safeMessages.size) {
            return RecyclerView.NO_ID
        }
        
        val message = safeMessages[position]
        // 使用消息ID的哈希码作为稳定ID
        return if (message.messageId.isNotEmpty()) {
            message.messageId.hashCode().toLong()
        } else {
            // 如果没有ID，则使用内容和时间戳的组合
            (message.content.hashCode() + message.timestamp).toLong()
        }
    }
    
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        if (position < 0 || position >= safeMessages.size) {
            return  // 防止索引越界
        }
        
        val message = safeMessages[position]
        
        if (message.isUser) {
            // 用户消息直接显示
            holder.messageText.text = message.content
        } else {
            // AI消息需要处理Markdown
            renderMarkdownContent(holder.messageText, message.content)
        }
    }
    
    override fun getItemViewType(position: Int): Int {
        if (position < 0 || position >= safeMessages.size) {
            return VIEW_TYPE_USER  // 默认返回用户类型，防止崩溃
        }
        return if (safeMessages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_AI
    }
    
    // 渲染Markdown内容
    private fun renderMarkdownContent(textView: TextView, markdownContent: String) {
        // 处理###等标题为粗体
        val formattedContent = processMarkdownHeaders(markdownContent)
        markwon?.setMarkdown(textView, formattedContent)
    }
    
    // 处理Markdown标题，使其更友好
    private fun processMarkdownHeaders(content: String): String {
        // 将###替换为粗体
        return content.replace(Regex("(#{1,6})\\s+(.+)")) { matchResult ->
            val headerLevel = matchResult.groupValues[1].length
            val headerText = matchResult.groupValues[2]
            when (headerLevel) {
                1 -> "**${headerText.uppercase()}**\n\n" // 一级标题：大写粗体
                2 -> "**${headerText}**\n\n"  // 二级标题：粗体
                else -> "__${headerText}__\n" // 其他级别：下划线
            }
        }
    }
    
    // 添加一条消息并通知适配器
    @Synchronized
    fun addMessage(message: AiMessage) {
        val position = safeMessages.size
        safeMessages.add(message)
        messages.clear()
        messages.addAll(safeMessages)
        notifyItemInserted(position)
    }
    
    // 更新一条消息并通知适配器
    @Synchronized
    fun updateMessage(position: Int, message: AiMessage) {
        if (position >= 0 && position < safeMessages.size) {
            safeMessages[position] = message
            messages.clear()
            messages.addAll(safeMessages)
            notifyItemChanged(position)
        }
    }
    
    // 使用ID查找消息位置
    @Synchronized
    fun findMessagePositionById(messageId: String): Int {
        return safeMessages.indexOfFirst { it.messageId == messageId }
    }
    
    // 移除消息
    @Synchronized
    fun removeMessage(position: Int) {
        if (position >= 0 && position < safeMessages.size) {
            safeMessages.removeAt(position)
            messages.clear()
            messages.addAll(safeMessages)
            notifyItemRemoved(position)
        }
    }
    
    // 替换所有消息
    @Synchronized
    fun replaceAllMessages(newMessages: List<AiMessage>) {
        val diffCallback = MessageDiffCallback(safeMessages.toList(), newMessages)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        
        safeMessages.clear()
        safeMessages.addAll(newMessages)
        
        messages.clear()
        messages.addAll(safeMessages)
        
        diffResult.dispatchUpdatesTo(this)
    }
    
    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageText: TextView = itemView.findViewById(R.id.messageText)
    }
    
    // DiffUtil用于高效更新列表
    private class MessageDiffCallback(
        private val oldList: List<AiMessage>,
        private val newList: List<AiMessage>
    ) : DiffUtil.Callback() {
        
        override fun getOldListSize() = oldList.size
        
        override fun getNewListSize() = newList.size
        
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldMessage = oldList[oldItemPosition]
            val newMessage = newList[newItemPosition]
            return oldMessage.messageId == newMessage.messageId || 
                   (oldMessage.timestamp == newMessage.timestamp && oldMessage.isUser == newMessage.isUser)
        }
        
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
} 