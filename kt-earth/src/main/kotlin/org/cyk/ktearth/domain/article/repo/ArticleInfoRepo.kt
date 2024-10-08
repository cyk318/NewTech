package org.cyk.ktearth.domain.article.repo

import org.cyk.ktearth.application.article.ArticlePageCmd
import org.cyk.ktearth.domain.article.domain.ArticleInfo
import org.cyk.ktearth.infra.model.PageResp

interface ArticleInfoRepo {

    fun save(o: ArticleInfo)

    fun page(cmd: ArticlePageCmd): PageResp<ArticleInfo>

    fun queryById(articleId: String): ArticleInfo?

    fun exists(articleId: String): Boolean

    fun removeById(articleId: String)

    fun update(o: ArticleInfo)

}