package org.cyk.ktearth.domain.uact.domain

enum class CommentStatus(
    val code: Int
) {
    //正常
    NORMAL(0),
    //审核
    AUDIT(1),
    //封禁
    BAN(2),


}

