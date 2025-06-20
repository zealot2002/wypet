package com.qh.wypet.ui.category

import com.qh.wypet.R

object CategoryDataProvider {

    fun getCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                name = "宠物食品",
                isSelected = true,
                subcategories = listOf(
                    Subcategory(id = 101, name = "猫粮", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 102, name = "狗粮", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 103, name = "零食", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 104, name = "营养品", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 105, name = "处方粮", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 106, name = "罐头", imageResId = R.drawable.ic_pet)
                )
            ),
            Category(
                id = 2,
                name = "宠物用品",
                subcategories = listOf(
                    Subcategory(id = 201, name = "猫砂", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 202, name = "猫砂盆", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 203, name = "餐具", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 204, name = "牵引绳", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 205, name = "笼子", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 206, name = "衣服", imageResId = R.drawable.ic_pet)
                )
            ),
            Category(
                id = 3,
                name = "宠物玩具",
                subcategories = listOf(
                    Subcategory(id = 301, name = "逗猫棒", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 302, name = "毛绒玩具", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 303, name = "飞盘", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 304, name = "互动玩具", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 305, name = "益智玩具", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 306, name = "爬架", imageResId = R.drawable.ic_pet)
                )
            ),
            Category(
                id = 4,
                name = "宠物医疗",
                subcategories = listOf(
                    Subcategory(id = 401, name = "驱虫药", imageResId = R.drawable.ic_cat_health),
                    Subcategory(id = 402, name = "皮肤药", imageResId = R.drawable.ic_cat_health),
                    Subcategory(id = 403, name = "眼耳清洁", imageResId = R.drawable.ic_cat_health),
                    Subcategory(id = 404, name = "肠胃调理", imageResId = R.drawable.ic_cat_health),
                    Subcategory(id = 405, name = "维生素", imageResId = R.drawable.ic_cat_health),
                    Subcategory(id = 406, name = "口腔护理", imageResId = R.drawable.ic_cat_health)
                )
            ),
            Category(
                id = 5,
                name = "宠物清洁",
                subcategories = listOf(
                    Subcategory(id = 501, name = "沐浴露", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 502, name = "梳子", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 503, name = "指甲剪", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 504, name = "除臭剂", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 505, name = "尿垫", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 506, name = "湿巾", imageResId = R.drawable.ic_pet)
                )
            ),
            Category(
                id = 6,
                name = "宠物服务",
                subcategories = listOf(
                    Subcategory(id = 601, name = "美容", imageResId = R.drawable.ic_services),
                    Subcategory(id = 602, name = "寄养", imageResId = R.drawable.ic_services),
                    Subcategory(id = 603, name = "训练", imageResId = R.drawable.ic_services),
                    Subcategory(id = 604, name = "遛狗", imageResId = R.drawable.ic_services),
                    Subcategory(id = 605, name = "宠物摄影", imageResId = R.drawable.ic_services),
                    Subcategory(id = 606, name = "宠物医疗", imageResId = R.drawable.ic_services)
                )
            ),
            Category(
                id = 7,
                name = "猫咪用品",
                subcategories = listOf(
                    Subcategory(id = 701, name = "猫抓板", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 702, name = "猫窝", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 703, name = "猫爬架", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 704, name = "猫咪衣服", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 705, name = "猫玩具", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 706, name = "猫咪零食", imageResId = R.drawable.ic_pet)
                )
            ),
            Category(
                id = 8,
                name = "狗狗用品",
                subcategories = listOf(
                    Subcategory(id = 801, name = "狗窝", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 802, name = "狗链", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 803, name = "狗衣服", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 804, name = "狗零食", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 805, name = "狗玩具", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 806, name = "狗饭盆", imageResId = R.drawable.ic_pet)
                )
            ),
            Category(
                id = 9,
                name = "宠物百科",
                subcategories = listOf(
                    Subcategory(id = 901, name = "养宠知识", imageResId = R.drawable.ic_encyclopedia),
                    Subcategory(id = 902, name = "疾病预防", imageResId = R.drawable.ic_encyclopedia),
                    Subcategory(id = 903, name = "行为训练", imageResId = R.drawable.ic_encyclopedia),
                    Subcategory(id = 904, name = "品种图鉴", imageResId = R.drawable.ic_encyclopedia),
                    Subcategory(id = 905, name = "喂养指南", imageResId = R.drawable.ic_encyclopedia),
                    Subcategory(id = 906, name = "繁育知识", imageResId = R.drawable.ic_encyclopedia)
                )
            ),
            Category(
                id = 10,
                name = "宠物社区",
                subcategories = listOf(
                    Subcategory(id = 1001, name = "晒宠", imageResId = R.drawable.ic_social),
                    Subcategory(id = 1002, name = "宠物救助", imageResId = R.drawable.ic_social),
                    Subcategory(id = 1003, name = "寻宠", imageResId = R.drawable.ic_social),
                    Subcategory(id = 1004, name = "宠友圈", imageResId = R.drawable.ic_social),
                    Subcategory(id = 1005, name = "宠物活动", imageResId = R.drawable.ic_social),
                    Subcategory(id = 1006, name = "宠物咨询", imageResId = R.drawable.ic_social)
                )
            ),
            Category(
                id = 11,
                name = "宠物商城",
                subcategories = listOf(
                    Subcategory(id = 1101, name = "热卖产品", imageResId = R.drawable.ic_cart),
                    Subcategory(id = 1102, name = "新品上市", imageResId = R.drawable.ic_cart),
                    Subcategory(id = 1103, name = "促销活动", imageResId = R.drawable.ic_cart),
                    Subcategory(id = 1104, name = "品牌专区", imageResId = R.drawable.ic_cart),
                    Subcategory(id = 1105, name = "积分兑换", imageResId = R.drawable.ic_gift),
                    Subcategory(id = 1106, name = "优惠券", imageResId = R.drawable.ic_gift)
                )
            ),
            Category(
                id = 12,
                name = "个人中心",
                subcategories = listOf(
                    Subcategory(id = 1201, name = "我的订单", imageResId = R.drawable.ic_order),
                    Subcategory(id = 1202, name = "我的宠物", imageResId = R.drawable.ic_pet),
                    Subcategory(id = 1203, name = "我的地址", imageResId = R.drawable.ic_address),
                    Subcategory(id = 1204, name = "我的收藏", imageResId = R.drawable.ic_favorite),
                    Subcategory(id = 1205, name = "我的关注", imageResId = R.drawable.ic_favorite),
                    Subcategory(id = 1206, name = "我的评价", imageResId = R.drawable.ic_comment)
                )
            )
        )
    }
} 