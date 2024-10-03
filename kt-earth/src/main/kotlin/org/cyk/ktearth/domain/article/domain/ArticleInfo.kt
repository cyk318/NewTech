package org.cyk.ktearth.domain.article.domain

import java.time.LocalDateTime

data class ArticleInfo (
    val id: String,
    val authorId: String,
    val title: String,
    val content: String,
    val cover: String, //文章封面
    val label: List<String>, //标签
    val type: Int, //文章类型
    val cTime: LocalDateTime,
    val uTime: LocalDateTime,
)
