package org.cyk.ktearth.domain.uact.domain

import java.time.LocalDateTime

data class ArticleLike (
    val id: String,
    val articleId: String,
    val userId: String,
    val uTime: LocalDateTime
)
