package org.cyk.ktduitang.domain.article.repo

import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.stereotype.Component
import java.util.Date

@Table(name = "article_tag")
data class ArticleTag (
    @Id
    val id: Long,
    val aId: Long,
    val uId: Long,
    val name: String,
    val cTime: Date,
)

@Component
class ArticleTagRepo {

}