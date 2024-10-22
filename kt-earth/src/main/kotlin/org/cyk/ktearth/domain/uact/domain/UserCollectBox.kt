package org.cyk.ktearth.domain.uact.domain

import java.util.Date

/**
 * 用户收藏夹
 * 关系: 一个用户可以创建多个收藏夹
 * 注意: 只支持收藏文章
 */
data class UserCollectBox (
    val id: String? = null,
    val userId: String,
    val name: String, //收藏夹名字
    val collects: MutableList<Collect> = mutableListOf(),
    val cTime: Date = Date(),
    var uTime: Date = Date(),
) {

    /**
     * 收藏提交
     * 1.收藏: 收藏存在，删除收藏信息
     * 2.取消收藏: 收藏信息不存在，新增收藏信息
     * Ps：取消收藏有一个前置条件 -> 先在数据库中查出用户要删除的 收藏信息(Collect) 在哪个 收藏夹(UserCollectBox)下.
     * @return 1新增 -1删除
     */
    fun submitCollect(targetId: String): Int {
        val exists = collects.any { it.targetId == targetId }
        val collectStatus = if (!exists) {
            this.collects.add(Collect(targetId = targetId))
            1
        } else {
            this.collects.removeIf { it.targetId == targetId }
            -1
        }
        this.uTime = Date()
        return collectStatus
    }

    /**
     * 对于是否有收藏，由于没有对应更外层的领域设计，
     * 就需要从数据库中查询得到该用户的收藏夹列表，然后流处理这个列表进行判断
     */

}

data class Collect (
    val targetId: String,
    val cTime: Date = Date(),
)