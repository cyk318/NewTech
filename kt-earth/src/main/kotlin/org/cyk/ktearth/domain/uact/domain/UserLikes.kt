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
     * Ps: mongo 中所有 id 都是唯一的，因此判断是否存在，只需根据 id 即可
     * @return 1新增 -1删除
     */
    fun submitLike(targetId: String, type: LikeType): Int {
        val like = likes.firstOrNull { it.targetId == targetId }
        val likeStatus = if (like != null) {
            this.likes.removeIf { it.targetId == targetId }
            -1
        } else {
            this.likes.add(Like(targetId = targetId, type = type))
            1
        }
        this.uTime = Date()
        return likeStatus
    }

}

data class Like(
    val targetId: String,
    val type: LikeType,
    val cTime: Date = Date(),
)

enum class LikeType(
    val code: Int,
) {
    /**
     * 文章
     */
    ARTICLE(1),

    /**
     * 评论
     */
    COMMENT(2),
    ;

    companion object {
        fun codeOf(code: Int): LikeType? = entries.firstOrNull { it.code == code }
    }

}
