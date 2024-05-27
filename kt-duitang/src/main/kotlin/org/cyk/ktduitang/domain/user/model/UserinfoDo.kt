package org.cyk.ktduitang.domain.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("user_ident")
data class UserIdentDo (
    @Id
    val id: Long? = null, // 用户 id
    val username: String, // 用户名
    val password: String, // 密码
    val state: Int, //状态: 0正常(默认) 1封号 2管理员
    val cTime: Date,
    val uTime: Date,
)

@Document("user_detail")
data class UserDetailDo (
    @Id
    val id: Long,
    val brief: String, // 简介
    val gender: Int, // 性别
    val birthday: String, // 生日
    val avatarPath: String, //头像路径
    val likes: List<Long>, //点赞列表(文章 id 列表)
    val collects: List<Long>, //收藏列表(文章 id 列表)
)
