package org.cyk.ktearth.domain.uact.domain

import java.time.LocalDateTime

data class CommentStat (
    val commentId: String,
    val likeCnt: Long,
    val commentCnt: Long,
    val uTime: LocalDateTime,
    val cTime: LocalDateTime,
)