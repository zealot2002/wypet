package com.qh.wypet.ui.aiqa

/**
 * 表示AI对话中的一条消息
 * @param content 消息内容
 * @param isUser 是否是用户发送的消息，true表示用户消息，false表示AI回复
 * @param timestamp 消息时间戳
 * @param messageId 消息的唯一标识，用于特殊消息的标记和处理
 */
data class AiMessage(
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val messageId: String = ""
) 