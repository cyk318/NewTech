package org.cyk.ktearth.domain.article.repo

import org.cyk.ktearth.domain.article.domain.ArticleStat

interface ArticleStatRepo {

    fun save(o: ArticleStat)

    fun queryByArticleIds(articleIds: List<String>): List<ArticleStat>

    fun delByArticleId(articleId: String)

    fun viewCntIncr(articleId: String)

    fun likeCntIncr(articleId: String)

    fun likeCntDecr(articleId: String)

    fun commentCntIncr(articleId: String)

}