package org.cyk.ktearth.application.article

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.domain.ArticleType
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile


class AdminArticleUpdateCmd (
    val authorId: String,
    val title: String,
    val content: String,
    val cover: MultipartFile,
    val label: List<String>,
    val type: Int,
)

@Component
class AdminArticleUpdateHandler(
    private val articleInfoRepo: ArticleInfoRepo,
): ApplicationHandler<AdminArticleUpdateCmd, Unit> {

    override fun handler(input: AdminArticleUpdateCmd) {
        ArticleType.of(input.type)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "非法 type: ${input.type}")

    }

}