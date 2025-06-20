package com.qh.wypet.ui.social

data class SocialFeedItem(
    val id: String,
    val username: String,
    val avatarUrl: String? = null,
    val timeAgo: String,
    val content: String,
    val imageUrl: String? = null,
    val likes: Int = 0,
    val comments: Int = 0,
    var isFavorite: Boolean = false
)

// Helper to generate sample data
object SampleDataProvider {
    
    fun getSampleRecommendedData(): List<SocialFeedItem> {
        return listOf(
            SocialFeedItem(
                id = "1",
                username = "小橘猫",
                timeAgo = "5分钟前",
                content = "今天和主人一起去了宠物公园，遇到了好多小伙伴，真开心！",
                likes = 128,
                comments = 32
            ),
            SocialFeedItem(
                id = "2",
                username = "布偶猫小萌",
                timeAgo = "30分钟前",
                content = "新买的猫爬架真好玩，我可以在上面睡午觉了~",
                likes = 85,
                comments = 14
            ),
            SocialFeedItem(
                id = "3",
                username = "加菲猫",
                timeAgo = "2小时前",
                content = "主人今天给我买了新的罐头，味道真不错，推荐给大家！",
                likes = 256,
                comments = 48
            )
        )
    }
    
    fun getSampleFollowingData(): List<SocialFeedItem> {
        return listOf(
            SocialFeedItem(
                id = "4",
                username = "小白",
                timeAgo = "10分钟前",
                content = "今天去宠物医院做了体检，医生说我很健康，继续保持！",
                likes = 67,
                comments = 12
            ),
            SocialFeedItem(
                id = "5",
                username = "小黑",
                timeAgo = "1小时前",
                content = "昨天在家拆家具，被主人发现了，今天只能乖乖吃素...",
                likes = 134,
                comments = 28
            )
        )
    }
    
    fun getSampleFamilyData(): List<SocialFeedItem> {
        return listOf(
            SocialFeedItem(
                id = "6",
                username = "毛球家族",
                timeAgo = "15分钟前",
                content = "我们家族今天迎来了新成员，一只可爱的小黑猫！大家欢迎~",
                likes = 320,
                comments = 76
            )
        )
    }
} 