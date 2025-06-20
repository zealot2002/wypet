package com.qh.wypet.ui.social

import com.qh.wypet.utils.ImageUrls

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
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "5分钟前",
                content = "今天和主人一起去了宠物公园，遇到了好多小伙伴，真开心！",
                imageUrl = ImageUrls.CAT_GIF_1,
                likes = 128,
                comments = 32
            ),
            SocialFeedItem(
                id = "2",
                username = "布偶猫小萌",
                avatarUrl = ImageUrls.USER_AVATAR_2,
                timeAgo = "30分钟前",
                content = "新买的猫爬架真好玩，我可以在上面睡午觉了~",
                imageUrl = ImageUrls.CAT_SUPPLIES_1,
                likes = 85,
                comments = 14
            ),
            SocialFeedItem(
                id = "3",
                username = "加菲猫",
                avatarUrl = ImageUrls.USER_AVATAR_3,
                timeAgo = "2小时前",
                content = "主人今天给我买了新的罐头，味道真不错，推荐给大家！",
                imageUrl = ImageUrls.CAT_FOOD_1,
                likes = 256,
                comments = 48
            ),
            SocialFeedItem(
                id = "4",
                username = "喵星人",
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "3小时前",
                content = "今天去宠物医院做了体检，医生说我非常健康！",
                imageUrl = ImageUrls.PET_HEALTH_1,
                likes = 78,
                comments = 23
            ),
            SocialFeedItem(
                id = "5",
                username = "猫咪甜甜",
                avatarUrl = ImageUrls.USER_AVATAR_2,
                timeAgo = "5小时前",
                content = "新买的猫薄荷玩具，我好喜欢！一直在玩个不停～",
                imageUrl = ImageUrls.CAT_MINT_1,
                likes = 312,
                comments = 45
            ),
            SocialFeedItem(
                id = "6",
                username = "喵大人",
                avatarUrl = ImageUrls.USER_AVATAR_3,
                timeAgo = "昨天",
                content = "猫砂盆更换了新款，自动清理太方便了，主人再也不用天天铲屎啦！",
                imageUrl = ImageUrls.CAT_LITTER_1,
                likes = 188,
                comments = 36
            ),
            SocialFeedItem(
                id = "7",
                username = "毛毛球",
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "昨天",
                content = "天气变热了，我的夏季护理开始啦！记得给猫咪多梳毛哦～",
                imageUrl = ImageUrls.CAT_GIF_15,
                likes = 267,
                comments = 52
            ),
            SocialFeedItem(
                id = "8",
                username = "橘子吖",
                avatarUrl = ImageUrls.USER_AVATAR_2,
                timeAgo = "2天前",
                content = "玩具老鼠大比拼，这款超级好玩，抓着不放手！",
                imageUrl = ImageUrls.CAT_TOY_1,
                likes = 156,
                comments = 31
            )
        )
    }
    
    fun getSampleFollowingData(): List<SocialFeedItem> {
        return listOf(
            SocialFeedItem(
                id = "9",
                username = "小白",
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "10分钟前",
                content = "今天去宠物医院做了体检，医生说我很健康，继续保持！",
                imageUrl = ImageUrls.PET_HEALTH_5,
                likes = 67,
                comments = 12
            ),
            SocialFeedItem(
                id = "10",
                username = "小黑",
                avatarUrl = ImageUrls.USER_AVATAR_2,
                timeAgo = "1小时前",
                content = "昨天在家拆家具，被主人发现了，今天只能乖乖吃素...",
                imageUrl = ImageUrls.CAT_GIF_7,
                likes = 134,
                comments = 28
            ),
            SocialFeedItem(
                id = "11",
                username = "咪咪酱",
                avatarUrl = ImageUrls.USER_AVATAR_3,
                timeAgo = "2小时前",
                content = "新的猫窝到货啦，超级舒服，一躺就不想起来了～",
                imageUrl = ImageUrls.CAT_SUPPLIES_7,
                likes = 98,
                comments = 18
            ),
            SocialFeedItem(
                id = "12",
                username = "花花",
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "昨天",
                content = "夏天到了，冰垫是必备的降温神器，大家记得给猫咪准备一个～",
                imageUrl = ImageUrls.CAT_SUPPLIES_15,
                likes = 201,
                comments = 43
            )
        )
    }
    
    fun getSampleFamilyData(): List<SocialFeedItem> {
        return listOf(
            SocialFeedItem(
                id = "13",
                username = "毛球家族",
                avatarUrl = ImageUrls.USER_AVATAR_2,
                timeAgo = "15分钟前",
                content = "我们家族今天迎来了新成员，一只可爱的小黑猫！大家欢迎~",
                imageUrl = ImageUrls.CAT_GIF_21,
                likes = 320,
                comments = 76
            ),
            SocialFeedItem(
                id = "14",
                username = "喵喵一家",
                avatarUrl = ImageUrls.USER_AVATAR_3,
                timeAgo = "3小时前",
                content = "家族聚餐日，给大家准备了美味的猫罐头大餐！",
                imageUrl = ImageUrls.CAT_FOOD_14,
                likes = 178,
                comments = 35
            ),
            SocialFeedItem(
                id = "15",
                username = "猫猫庄园",
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "昨天",
                content = "大家族的合照时间到啦！看看谁是最上镜的～",
                imageUrl = ImageUrls.CAT_GIF_25,
                likes = 267,
                comments = 48
            )
        )
    }
} 