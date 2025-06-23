package com.qh.wypet.ui.social

import com.qh.wypet.utils.ImageUrls
import com.qh.wypet.utils.PetPhotographyImages

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
        val shortTexts = listOf(
            "今天和主人一起去了宠物公园！",
            "新买的猫爬架真好玩~",
            "主人今天给我买了新的罐头",
            "喵呜，今天心情好棒",
            "晒太阳真舒服",
            "这个逗猫棒太好玩了",
            "我的新玩具",
            "新猫窝超级舒适",
            "下雨天就是要睡觉",
            "铲屎官今天又给我买好吃的啦"
        )
        
        val mediumTexts = listOf(
            "今天和主人一起去了宠物公园，遇到了好多小伙伴，真开心！",
            "新买的猫爬架真好玩，我可以在上面睡午觉了~",
            "主人今天给我买了新的罐头，味道真不错，推荐给大家！",
            "今天去宠物医院做了体检，医生说我非常健康！",
            "新买的猫薄荷玩具，我好喜欢！一直在玩个不停～",
            "天气变热了，我的夏季护理开始啦！记得给猫咪多梳毛哦～",
            "玩具老鼠大比拼，这款超级好玩，抓着不放手！",
            "这个猫抓板质量真不错，我每天都要磨爪子，很耐用。",
            "自制逗猫棒其实很简单，铲屎官可以试试看哦！",
            "我家的阳台改造成了猫咪花园，有很多可以爬的架子。"
        )
        
        val longTexts = listOf(
            "猫砂盆更换了新款，自动清理太方便了，主人再也不用天天铲屎啦！而且除臭效果特别好，家里一点味道都没有，太赞了！",
            "今天尝试了一款新口味的猫罐头，鲜虾口味的，超级美味！我一口气吃了一整罐，铲屎官都惊呆了，这个品牌真的太棒了，强烈推荐给大家！",
            "夏天到了，冰垫是必备的降温神器，大家记得给猫咪准备一个～我每天下午都要趴在上面降温，舒服极了，猫咪过夏天的必备神器！",
            "昨天在家拆家具，被主人发现了，今天只能乖乖吃素...不过今天主人心情好像变好了，刚才又给我买了新的玩具，嘿嘿，装可怜有用！",
            "今天和主人一起看了宠物医生的视频，学习了很多猫咪护理知识，才知道我们每天都要刷牙，太可怕了，但为了健康我决定配合一下！",
            "家里来了一只新的小奶猫，好像要和我抢地盘，我决定先保持高冷姿态观察几天，让它知道谁才是这个家的大猫。不过它好像完全不怕我...",
            "今天主人给我洗了澡，我挣扎了很久，但是没用...洗完之后被吹风机吹得像个蓬松的棉花糖，不过感觉还挺舒服的，就是面子上有点过不去。",
            "主人今天带我去看了兽医，打了疫苗，好疼啊！不过医生说我很健康，体重控制得也很好。回家路上还去了宠物店，主人奖励了我新玩具！",
            "我家的智能喂食器坏了，主人不在家，我等了好久都没有粮食出来，差点饿扁了！幸好主人下班回来及时发现了，立刻给我加了双倍的食物补偿我！",
            "铲屎官新买的逗猫棒太高级了，有电动马达会自动晃动，我已经玩了一整天都不想停下来，太上瘾了！其他猫咪们如果看到这条一定要让主人也买一个！"
        )
        
        val usernames = listOf(
            "小橘猫", "布偶猫小萌", "加菲猫", "喵星人", "猫咪甜甜", 
            "喵大人", "毛毛球", "橘子吖", "小黑", "小白",
            "咪咪酱", "花花", "奶油", "点点", "小灰",
            "肥肥", "豆豆", "奶茶", "可可", "糖糖",
            "波波", "多多", "米米", "西瓜", "饭团",
            "黑豆", "汤圆", "雪球", "葡萄", "牛奶"
        )
        
        val timeAgoOptions = listOf(
            "刚刚", "1分钟前", "5分钟前", "10分钟前", "15分钟前", 
            "30分钟前", "1小时前", "2小时前", "3小时前", "4小时前", 
            "5小时前", "6小时前", "昨天", "2天前", "3天前"
        )
        
        val avatars = listOf(
            ImageUrls.USER_AVATAR_1, ImageUrls.USER_AVATAR_2, ImageUrls.USER_AVATAR_3
        )
        
        val result = mutableListOf<SocialFeedItem>()
        
        // Generate 30 items with varied text lengths
        for (i in 1..30) {
            val textType = when (i % 3) {
                0 -> shortTexts.random()
                1 -> mediumTexts.random()
                else -> longTexts.random()
            }
            
            result.add(
                SocialFeedItem(
                    id = i.toString(),
                    username = usernames[(i - 1) % usernames.size],
                    avatarUrl = avatars.random(),
                    timeAgo = timeAgoOptions.random(),
                    content = textType,
                    imageUrl = PetPhotographyImages.IMAGE_LINKS[(i - 1) % PetPhotographyImages.IMAGE_LINKS.size],
                    likes = (10..500).random(),
                    comments = (0..100).random()
                )
            )
        }
        
        return result
    }
    
    fun getSampleFollowingData(): List<SocialFeedItem> {
        return listOf(
            SocialFeedItem(
                id = "9",
                username = "小白",
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "10分钟前",
                content = "今天去宠物医院做了体检，医生说我很健康，继续保持！",
                imageUrl = PetPhotographyImages.IMAGE_LINKS[8],
                likes = 67,
                comments = 12
            ),
            SocialFeedItem(
                id = "10",
                username = "小黑",
                avatarUrl = ImageUrls.USER_AVATAR_2,
                timeAgo = "1小时前",
                content = "昨天在家拆家具，被主人发现了，今天只能乖乖吃素...",
                imageUrl = PetPhotographyImages.IMAGE_LINKS[9],
                likes = 134,
                comments = 28
            ),
            SocialFeedItem(
                id = "11",
                username = "咪咪酱",
                avatarUrl = ImageUrls.USER_AVATAR_3,
                timeAgo = "2小时前",
                content = "新的猫窝到货啦，超级舒服，一躺就不想起来了～",
                imageUrl = PetPhotographyImages.IMAGE_LINKS[10],
                likes = 98,
                comments = 18
            ),
            SocialFeedItem(
                id = "12",
                username = "花花",
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "昨天",
                content = "夏天到了，冰垫是必备的降温神器，大家记得给猫咪准备一个～",
                imageUrl = PetPhotographyImages.IMAGE_LINKS[11],
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
                imageUrl = PetPhotographyImages.IMAGE_LINKS[12],
                likes = 320,
                comments = 76
            ),
            SocialFeedItem(
                id = "14",
                username = "喵喵一家",
                avatarUrl = ImageUrls.USER_AVATAR_3,
                timeAgo = "3小时前",
                content = "家族聚餐日，给大家准备了美味的猫罐头大餐！",
                imageUrl = PetPhotographyImages.IMAGE_LINKS[13],
                likes = 178,
                comments = 35
            ),
            SocialFeedItem(
                id = "15",
                username = "猫猫庄园",
                avatarUrl = ImageUrls.USER_AVATAR_1,
                timeAgo = "昨天",
                content = "大家族的合照时间到啦！看看谁是最上镜的～",
                imageUrl = PetPhotographyImages.IMAGE_LINKS[14],
                likes = 267,
                comments = 48
            )
        )
    }
} 