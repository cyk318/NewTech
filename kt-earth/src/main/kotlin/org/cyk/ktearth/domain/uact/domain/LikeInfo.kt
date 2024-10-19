package org.cyk.ktearth.domain.uact.domain

import java.util.Date

data class LikeInfo (
    val id: String,
    val postId: String,
    val targetId: String,
    val cTime: Date,
)
