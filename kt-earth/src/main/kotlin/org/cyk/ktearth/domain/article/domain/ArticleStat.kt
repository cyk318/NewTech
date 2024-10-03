package org.cyk.ktearth.domain.article.domain

import java.time.LocalDateTime

data class ArticleStat (
    val articleId: String,
    val likeCnt: Long,
    val viewCnt: Long,
    val collectCnt: Long,
    val commentCnt: Long,
    val cTime: LocalDateTime,
    val uTime: LocalDateTime,
)
