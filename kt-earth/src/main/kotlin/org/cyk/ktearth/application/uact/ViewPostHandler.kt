package org.cyk.ktearth.application.uact

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.domain.ArticleType
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.domain.article.repo.ArticleStatRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component

data class ViewPostCmd (
    val targetId: String,
    val postId: String,
)

@Component
class ViewPostHandler(
    private val articleInfoRepo: ArticleInfoRepo,
    private val articleStatRepo: ArticleStatRepo,
): ApplicationHandler<ViewPostCmd, Unit> {

    override fun handler(input: ViewPostCmd) {
        // 文章必须存在
        val article = articleInfoRepo.queryById(input.targetId)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "文章不存在  articleId: ${input.targetId}")
        // 文章状态必须是 NORMAL
        if (article.type != ArticleType.NORMAL) {
            throw AppException(ApiStatus.INVALID_REQUEST, "文章状态必须是 NORMAL   article: $article")
        }

        // 访问量 +1
        val stat = articleStatRepo.queryByArticleId(article.id!!)
            ?: throw AppException(ApiStatus.INVALID_PARAM, "文章对应的统计表呢   articleId: ${article.id}")
        stat.incrViewCnt()
        articleStatRepo.save(stat)
    }

}


