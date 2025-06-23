package com.qh.wypet.ui.encyclopedia

import androidx.annotation.DrawableRes
import com.qh.wypet.R
import com.qh.wypet.utils.ImageUrls
import android.R.drawable as AndroidDrawable

// 分类导航
data class EncyclopediaCategory(
    val id: String,
    val name: String,
    @DrawableRes val iconResId: Int,
    val description: String
)

// 问题模型
data class Question(
    val id: String,
    val categoryId: String,
    val title: String,
    val content: String,
    val isBookmarked: Boolean = false
)

// 专题模型 - 修改为使用URL
data class Topic(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,  // 改为使用URL
    val articleCount: Int
)

// 文章模型 - 修改为使用URL
data class Article(
    val id: String,
    val title: String,
    val description: String,
    val content: String,
    val imageUrl: String,  // 改为使用URL
    val tag: String,
    val publishDate: String,
    val viewCount: Int = 0
)

/**
 * 提供百科相关数据
 */
object EncyclopediaDataProvider {
    
    // 获取分类列表
    fun getCategories(): List<EncyclopediaCategory> {
        return listOf(
            EncyclopediaCategory(
                id = "health",
                name = "宠物健康",
                iconResId = R.drawable.ic_cat_health,
                description = "疾病预防、常见健康问题"
            ),
            EncyclopediaCategory(
                id = "nutrition",
                name = "饲养技巧",
                iconResId = R.drawable.ic_cat_info,
                description = "猫咪正确饮食、营养均衡"
            ),
            EncyclopediaCategory(
                id = "behavior",
                name = "行为训练",
                iconResId = R.drawable.ic_encyclopedia,
                description = "如厕训练、抓挠行为矫正"
            ),
            EncyclopediaCategory(
                id = "disease",
                name = "疾病防治",
                iconResId = R.drawable.ic_cat_health,
                description = "常见猫病预防与治疗"
            ),
            EncyclopediaCategory(
                id = "breed",
                name = "品种百科",
                iconResId = R.drawable.ic_cat_info,
                description = "猫咪品种特征介绍"
            ),
            EncyclopediaCategory(
                id = "nutrition",
                name = "营养饮食",
                iconResId = R.drawable.ic_encyclopedia,
                description = "猫咪饮食营养搭配"
            )
        )
    }
    
    // 获取热门专题
    fun getHotTopics(): List<Topic> {
        return listOf(
            Topic(
                id = "kitten_guide",
                title = "幼猫成长指南",
                description = "包含断奶、疫苗、驱虫等新手知识",
                imageUrl = ImageUrls.Encyclopedia.KITTEN_GUIDE,
                articleCount = 12
            ),
            Topic(
                id = "disease_atlas",
                title = "猫咪常见疾病图谱",
                description = "图文并茂的疾病识别与处理方法",
                imageUrl = ImageUrls.Encyclopedia.DISEASE_ATLAS,
                articleCount = 8
            ),
            Topic(
                id = "emergency_guide",
                title = "宠物急救手册",
                description = "突发状况下的应急处理步骤",
                imageUrl = ImageUrls.Encyclopedia.EMERGENCY_GUIDE,
                articleCount = 6
            )
        )
    }
    
    // 获取最新文章
    fun getLatestArticles(): List<Article> {
        return listOf(
            Article(
                id = "a1",
                title = "猫咪饮食指南：如何科学喂养你的爱猫",
                description = "科学的猫咪饮食习惯对猫咪的健康至关重要。本文将介绍猫咪的营养需求，以及如何选择适合的猫粮...",
                content = "详细内容...",
                imageUrl = ImageUrls.Encyclopedia.CAT_NUTRITION,
                tag = "饮食",
                publishDate = "2023-06-15"
            ),
            Article(
                id = "a2",
                title = "猫咪行为解析：为什么猫咪会突然狂奔？",
                description = "猫咪狂奔（又称疯跑、夜间撒欢）是一种常见的猫咪行为，本文将从猫咪心理学角度解析这种行为背后的原因...",
                content = "详细内容...",
                imageUrl = ImageUrls.Encyclopedia.CAT_BEHAVIOR,
                tag = "行为",
                publishDate = "2023-06-10"
            ),
            Article(
                id = "a3",
                title = "新手铲屎官必读：如何让猫咪适应新家",
                description = "把一只猫咪带回家是令人兴奋的，但对猫咪来说这可能是个充满压力的过程。本文将指导新手铲屎官如何帮助猫咪平稳过渡...",
                content = "详细内容...",
                imageUrl = ImageUrls.Encyclopedia.NEW_OWNER,
                tag = "新手",
                publishDate = "2023-06-05"
            ),
            Article(
                id = "a4",
                title = "季节性护理：夏季如何帮助猫咪防暑降温",
                description = "随着气温升高，猫咪也会感到不适。本文介绍几种有效的方法帮助猫咪在炎热夏季保持舒适...",
                content = "详细内容...",
                imageUrl = ImageUrls.Encyclopedia.SEASONAL_CARE,
                tag = "护理",
                publishDate = "2023-06-01"
            )
        )
    }
    
    // 根据分类ID获取问题列表
    fun getQuestionsByCategory(categoryId: String): List<Question> {
        return when(categoryId) {
            "nutrition" -> listOf(
                Question(
                    id = "n1",
                    categoryId = "nutrition",
                    title = "猫咪每天应该喂食几次？",
                    content = "成猫通常每天喂食2-3次，幼猫则需要更频繁，每天3-4次。特别需要注意的是，干粮和湿粮应该搭配喂食，并确保猫咪随时有新鲜的饮用水。"
                ),
                Question(
                    id = "n2",
                    categoryId = "nutrition",
                    title = "猫咪可以吃哪些人类食物？",
                    content = "大多数人类食物不适合猫咪食用。少量煮熟的鸡肉、火鸡肉和鱼肉通常是安全的。但巧克力、葱、蒜、葡萄、葡萄干对猫咪有毒，应严格避免。"
                ),
                Question(
                    id = "n3",
                    categoryId = "nutrition",
                    title = "如何选择适合的猫粮？",
                    content = "选择猫粮时，应查看配料表，确保肉类蛋白是第一位，避免选择含有过多谷物填充物的产品。同时要考虑猫咪的年龄、体重和健康状况选择专用猫粮。"
                )
            )
            "health" -> listOf(
                Question(
                    id = "h1",
                    categoryId = "health",
                    title = "猫咪疫苗接种时间表",
                    content = "幼猫应在8-9周龄开始接种基础疫苗，包括猫瘟、猫杯状病毒和猫鼻气管炎三联疫苗，随后每3-4周追加一次，直至16周龄。成年后，每年需进行加强免疫。"
                ),
                Question(
                    id = "h2",
                    categoryId = "health",
                    title = "猫咪常见体外寄生虫有哪些？",
                    content = "常见的体外寄生虫包括跳蚤、蜱虫和耳螨。定期使用驱虫药物进行预防很重要，特别是户外活动的猫咪。发现寄生虫时应及时治疗，并清洁猫咪的生活环境。"
                )
            )
            else -> emptyList()
        }
    }
} 