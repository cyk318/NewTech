package org.cyk.ktearth.domain.article.domain

import java.util.Date

data class ArticleStat (
    val articleId: String,
    val likeCnt: Long = 0,
    val viewCnt: Long = 0,
    val collectCnt: Long = 0,
    val commentCnt: Long = 0,
    val cTime: Long = Date().time,
    val uTime: Long = Date().time,
)
