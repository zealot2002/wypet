package com.qh.wypet.ui.encyclopedia

/**
 * 文章段落类型
 */
enum class ParagraphType {
    TEXT,       // 纯文本段落
    IMAGE,      // 图片段落
    SUBTITLE    // 小标题段落
}

/**
 * 文章段落模型
 */
data class ArticleParagraph(
    val type: ParagraphType,
    val content: String,          // 文本内容（对于TEXT和SUBTITLE）或者图片说明文本（对于IMAGE）
    val imageUrl: String? = null  // 仅对于IMAGE类型有效
) 