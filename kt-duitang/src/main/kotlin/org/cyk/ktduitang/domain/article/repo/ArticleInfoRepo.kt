package org.cyk.ktduitang.domain.article.repo

import jakarta.persistence.*
import org.cyk.ktduitang.infra.template.DatabaseTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Table(name = "article_info")
data class ArticleInfoDo (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null, // 文章 id
    val uId: Long, // 用户 id
    val content: String, // 文章内容
    val state: String, // 文章状态: 0正常 1草稿 2封禁
    val cTime: Date,
    val uTime: Date,
)

@Component
class ArticleInfoRepo(
    val databaseTemplate: DatabaseTemplate<ArticleInfoDo, Long>
) {



}