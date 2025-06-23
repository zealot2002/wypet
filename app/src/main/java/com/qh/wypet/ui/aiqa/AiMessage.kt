package com.qh.wypet.ui.aiqa

/**
 * 表示AI对话中的一条消息
 * @param content 消息内容
 * @param isUser 是否是用户发送的消息，true表示用户消息，false表示AI回复
 */
data class AiMessage(
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
) 