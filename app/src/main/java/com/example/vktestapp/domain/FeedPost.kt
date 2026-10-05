package com.example.vktestapp.domain

import com.example.vktestapp.R

data class FeedPost(
    val communityName: String = "Название группы вк",
    val publicationDate: String = "05.10.2026",
    val avatarResId: Int = R.drawable.comuniti_pic,
    val contentText: String = "Text поста Text поста Text поста Text поста Text поста Text поста Text поста",
    val contentImageResId: Int = R.drawable.post_pic,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 966),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.COMMENTS, count = 8),
        StatisticItem(type = StatisticType.LIKES, count = 27),

    )
)
