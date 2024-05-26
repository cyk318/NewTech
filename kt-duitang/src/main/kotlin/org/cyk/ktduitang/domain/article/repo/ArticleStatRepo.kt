package org.cyk.ktduitang.domain.article.repo

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Component
import java.util.Date

@Document("article_stat")
data class ArticleStatDo (
    @Id
    val id: Long, // 文章 id
    val pv: Long, // 访问量
    val likeCnt: Long, // 点赞量
    val collectCnt: Long, // 收藏量
    val commentCnt: Long, // 评论量
    val uTime: Date,
)

@Component
class ArticleStatRepo {

}