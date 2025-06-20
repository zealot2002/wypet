package com.qh.wypet.utils

object PetPhotographyImages {
    // 宠物生活摄影图片链接集合
    val IMAGE_LINKS = listOf(
        "https://img0.baidu.com/it/u=912009130,639523745&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img0.baidu.com/it/u=1089463153,3524088314&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img0.baidu.com/it/u=458955360,3146186337&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=213057129,1755463485&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=573856823,1648219974&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=475463749,2570584076&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1200",
        "https://img0.baidu.com/it/u=3910890035,2817712099&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img0.baidu.com/it/u=1140564839,3638363848&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=2597519409,434608360&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=755",
        "https://img2.baidu.com/it/u=1372885430,3486329643&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1200",
        "https://img2.baidu.com/it/u=3858296103,733483313&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667",
        "https://img0.baidu.com/it/u=1345015795,3437720843&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667",
        "https://img0.baidu.com/it/u=2164508297,1870184936&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=3109878409,2073231867&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667",
        "https://img1.baidu.com/it/u=4146480100,3198836539&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=749",
        "https://img1.baidu.com/it/u=1616439527,3582269019&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img0.baidu.com/it/u=2675278934,1429905403&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img2.baidu.com/it/u=748482504,795571763&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img0.baidu.com/it/u=2994118205,962129103&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1200",
        "https://img1.baidu.com/it/u=1939889845,439720787&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=1312921445,1359009374&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=3922223281,3692996791&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img0.baidu.com/it/u=1350547657,2502608487&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1200",
        "https://img1.baidu.com/it/u=2765415761,501583182&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=3778827397,688355324&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=1200",
        "https://img0.baidu.com/it/u=3072532720,1440358502&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=1311032648,2337631494&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=11756250,3672219396&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img0.baidu.com/it/u=2170017189,4274063034&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750",
        "https://img1.baidu.com/it/u=1540939005,3660058440&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=750"
    )

    // 获取随机图片链接的函数
    fun getRandomImageUrl(): String = IMAGE_LINKS.random()
}