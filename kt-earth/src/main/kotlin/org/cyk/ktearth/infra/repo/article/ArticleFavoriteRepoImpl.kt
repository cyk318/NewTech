package org.cyk.blog.infra.repo.article

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Repository
import java.util.Date

@Document("article_favorite")
data class ArticleFavoriteDo (
    @Id
    val id: String? = null,
    val postId: String,
    val targetId: String,
    val cTime: Date = Date()
)
data class ArticleFavorite (
    @Id
    val id: String? = null,
    val postId: String,
    val targetId: String,
    val cTime: Date = Date()
)

//@Repository
//class ArticleFavoriteRepoImpl: ArticleFavoriteRepo {
//
//}
//
