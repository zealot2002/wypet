package com.qh.wypet.ui.social

data class SocialCommentModel(
    val id: String,
    val userId: String,
    val userName: String,
    val userAvatar: String,
    val content: String,
    val date: String,
    val likes: Int = 0,
    val isLiked: Boolean = false,
    val replyTo: String? = null,  // 回复目标评论的ID
    val replyToUsername: String? = null, // 回复目标用户名
    val replies: List<SocialCommentModel> = emptyList(), // 嵌套回复列表
    val location: String? = null  // 评论地点，如广东省，上海市
) 