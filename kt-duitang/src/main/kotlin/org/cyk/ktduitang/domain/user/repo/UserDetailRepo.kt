package org.cyk.ktduitang.domain.user.repo

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document

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

class UserDetailRepo {

}