package org.cyk.ktduitang.domain.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("user_ident")
data class UserIdentDo (
    @Id
    val id: String? = null, // 用户 id
    val username: String, // 用户名
    val password: String, // 密码
    val state: Int = 0, //状态: 0正常(默认) 1封号 2管理员
    val cTime: Date = Date(),
    val uTime: Date = Date(),
)

@Document("user_detail")
data class UserDetailDo (
    @Id
    val id: String,
    val brief: String = "这个人很懒，什么都没有留下", // 简介
    val gender: Int = 1, // 性别: 0保密(默认) 1男 2女
    val birthday: String = "", // 生日: 默认空字符串
    val avatarPath: String = "~/app/user/avatar/default.png", //头像路径
    val likes: MutableList<Long> = mutableListOf(), //点赞列表(文章 id 列表)
    val collects: MutableList<Long> = mutableListOf(), //收藏列表(文章 id 列表)
)
