package org.cyk.ktearth.domain.uact.domain

import java.util.Date

data class CommentStat (
    val commentId: String,
    val likeCnt: Long,
    val commentCnt: Long,
    val uTime: Date,
    val cTime: Date,
)
