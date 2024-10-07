package org.cyk.ktearth.application.article

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.domain.ArticleInfo
import org.cyk.ktearth.domain.article.domain.ArticleStat
import org.cyk.ktearth.domain.article.domain.ArticleType
import org.cyk.ktearth.domain.article.repo.ArticleCoverRepo
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.domain.article.repo.ArticleStatRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.Date
import kotlin.reflect.typeOf

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
        ArticleType.of(input.type)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "非法 type: ${input.type}")

        putDb(input)
    }

    /**
     * 落库
     * desc: 落库的顺序不能变！
     */
    private fun putDb(input: AdminArticlePubCmd) {
        //文章封面
        val coverPath = articleCoverRepo.save(input.cover)

        // 文章信息
        val info = with(input) {
            ArticleInfo (
                authorId = authorId,
                title = title,
                content = content,
                cover = coverPath,
                label = label,
                type = ArticleType.of(input.type)!!,
            )
        }
        articleInfoRepo.save(info)

        //文章统计
        val stat = ArticleStat (
            articleId = info.id!!,
        )
        articleStatRepo.save(stat)
    }

}
