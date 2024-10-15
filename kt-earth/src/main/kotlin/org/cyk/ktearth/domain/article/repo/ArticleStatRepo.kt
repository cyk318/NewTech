package org.cyk.ktearth.domain.article.repo

import org.cyk.ktearth.domain.article.domain.ArticleStat

interface ArticleStatRepo {

    fun save(o: ArticleStat)

    fun queryByArticleIds(articleIds: List<String>): List<ArticleStat>
    fun queryByArticleId(articleId: String): ArticleStat?

}