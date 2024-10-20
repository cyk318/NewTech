package org.cyk.ktearth.domain.uact.domain

import java.util.Date

data class UserLikes(
    val userId: String,
    val likes: MutableList<Like> = mutableListOf(),
    val cTime: Date = Date(),
    var uTime: Date = Date(),
) {

    fun hasLike(targetId: String): Boolean {
        return this.likes.any { it.targetId == targetId }
    }

    /**
     * 点赞提交
     * 1.点赞存在: 删除.
     * 2.点赞不存在: 新增.
     */
    fun submitLike(targetId: String) {
        val like = likes.firstOrNull { it.targetId == targetId }
        if (like != null) {
            this.likes.removeIf { it.targetId == targetId }
        } else {
            this.likes.add(Like(targetId = targetId))
        }
        this.uTime = Date()
    }

}

data class Like(
    val targetId: String,
    val cTime: Date = Date(),
)