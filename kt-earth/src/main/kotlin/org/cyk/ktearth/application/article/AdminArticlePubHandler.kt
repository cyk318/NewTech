package org.cyk.ktearth.application.article

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.repo.ArticleCoverRepo
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.domain.article.repo.ArticleStatRepo
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

class AdminArticlePubCmd (
    val authorId: String,
    val title: String,
    val content: String,
    val cover: MultipartFile,
    val label: List<String>,
    val type: Int,
)

@Component
class AdminArticlePubHandler(
    private val articleInfoRepo: ArticleInfoRepo,
    private val articleCoverRepo: ArticleCoverRepo,
    private val articleStatRepo: ArticleStatRepo,
): ApplicationHandler<AdminArticlePubCmd, Unit> {

    override fun handler(input: AdminArticlePubCmd) {

    }

}
