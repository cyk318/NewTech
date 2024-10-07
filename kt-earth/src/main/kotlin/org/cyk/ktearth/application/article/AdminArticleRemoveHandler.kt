package org.cyk.ktearth.application.article

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.domain.article.repo.ArticleStatRepo
import org.cyk.ktearth.domain.oss.repo.OssFileRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

data class AdminArticleRemoveCmd (
    val articleId: String,
)

@Component
class AdminArticleRemoveHandler(
    private val articleInfoRepo: ArticleInfoRepo,
    private val articleStatRepo: ArticleStatRepo,
    private val ossFileRepo: OssFileRepo,
): ApplicationHandler<AdminArticleRemoveCmd, Unit> {

    @Value("\${minio.bucket.article.cover}")
    private lateinit var bucket: String

    override fun handler(input: AdminArticleRemoveCmd) {
        val article = articleInfoRepo.queryById(input.articleId)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "文章不存在  articleId: ${input.articleId}")

        ossFileRepo.remove(bucket, article.authorId, article.cover)
        articleStatRepo.removeById(input.articleId)
        articleInfoRepo.removeById(input.articleId)
    }

}
