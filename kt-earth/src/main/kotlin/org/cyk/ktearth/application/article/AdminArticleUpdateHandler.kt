package org.cyk.ktearth.application.article

import org.cyk.ktearth.application.ApplicationHandler
import org.cyk.ktearth.domain.article.domain.ArticleInfo
import org.cyk.ktearth.domain.article.domain.ArticleType
import org.cyk.ktearth.domain.article.repo.ArticleInfoRepo
import org.cyk.ktearth.domain.oss.repo.OssFileRepo
import org.cyk.ktearth.infra.exception.AppException
import org.cyk.ktearth.infra.model.ApiStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.Date

class AdminArticleUpdateCmd (
    val operatorId: String,
    val id: String,
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
    private val ossFileRepo: OssFileRepo,
): ApplicationHandler<AdminArticleUpdateCmd, Unit> {

    @Value("\${minio.bucket.article.cover}")
    private lateinit var bucket: String

    override fun handler(input: AdminArticleUpdateCmd) {
        val type = ArticleType.of(input.type)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "非法 type: ${input.type}")
        val article = articleInfoRepo.queryById(input.id)
            ?: throw AppException(ApiStatus.INVALID_REQUEST, "文章 id 不存在: ${input.id} ")
        if (article.authorId != input.operatorId) {
            throw AppException(ApiStatus.INVALID_REQUEST, "用户 id 非法  authorId: ${article.authorId} operator: ${input.operatorId}")
        }

        //封面
        ossFileRepo.remove(bucket, article.authorId, article.cover)
        val coverPath = ossFileRepo.save(bucket, article.authorId, input.cover)

        //专辑内容
        val o = ArticleInfo (
            id = input.id,
            authorId = input.authorId,
            title = input.title,
            content = input.content,
            cover = coverPath,
            label = input.label,
            type = type,
            uTime = Date().time
        )
        articleInfoRepo.update(o)
    }

}