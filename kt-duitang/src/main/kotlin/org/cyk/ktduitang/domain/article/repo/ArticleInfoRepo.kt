package org.cyk.ktduitang.domain.article.repo

import org.springframework.stereotype.Component
import java.util.*

data class ArticleInfoDo (
    val id: Long? = null, // 文章 id
    val uId: Long, // 用户 id
    val content: String, // 文章内容
    val state: String, // 文章状态: 0正常 1草稿 2封禁
    val cTime: Date,
    val uTime: Date,
)

@Component
class ArticleInfoRepo(
) {



}