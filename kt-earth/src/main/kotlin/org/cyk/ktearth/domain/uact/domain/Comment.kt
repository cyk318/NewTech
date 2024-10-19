package org.cyk.ktearth.domain.uact.domain

import java.time.LocalDateTime

data class CommentInfo (
    val id: String,
    val articleId: String,
    val parentId: String, //父评论id，默认为顶级评论 val userId: String,
    val userId: String,
    val content: String,
    val status: Int, //默认状态为 审核
    val cTime: LocalDateTime,
    val uTime: LocalDateTime,
)

enum class CommentParentIdType(
    val id: String
) {

    TOP("0"), //顶级评论id = 0

}

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

