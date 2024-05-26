package org.cyk.ktduitang.domain.article.repo

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.cyk.ktduitang.infra.template.DatabaseTemplate
import org.springframework.stereotype.Component
import java.util.*

@TableName(value = "article_info")
data class ArticleInfoDo (
    @TableId
    val id: Long? = null, // 文章 id
    val uId: Long, // 用户 id
    val content: String, // 文章内容
    val state: String, // 文章状态: 0正常 1草稿 2封禁
    val cTime: Date,
    val uTime: Date,
)

@Component
class ArticleInfoRepo(
    val databaseTemplate: DatabaseTemplate<ArticleInfoDo>
) {



}