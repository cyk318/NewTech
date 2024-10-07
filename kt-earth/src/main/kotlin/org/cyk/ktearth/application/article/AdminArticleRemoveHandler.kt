package org.cyk.ktearth.application.article

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.repo.ArticleCoverRepo
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.domain.article.repo.ArticleStatRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component

data class AdminArticleRemoveCmd (
    val articleId: String,
)

@Component
class AdminArticleRemoveHandler(
    private val articleInfoRepo: ArticleInfoRepo,
    private val articleCoverRepo: ArticleCoverRepo,
    private val articleStatRepo: ArticleStatRepo,
): ApplicationHandler<AdminArticleRemoveCmd, Unit> {

    override fun handler(input: AdminArticleRemoveCmd) {
        val article = articleInfoRepo.queryById(input.articleId)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "文章不存在  articleId: ${input.articleId}")

        articleCoverRepo.removeByCover(article.cover)
        articleStatRepo.removeById(input.articleId)
        articleInfoRepo.removeById(input.articleId)
    }

}
