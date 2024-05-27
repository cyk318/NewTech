package org.cyk.ktduitang.domain.article.repo

import org.springframework.stereotype.Component
import java.util.Date

data class ArticleTag (
    val id: Long,
    val aId: Long,
    val uId: Long,
    val name: String,
    val cTime: Date,
)

@Component
class ArticleTagRepo {

}