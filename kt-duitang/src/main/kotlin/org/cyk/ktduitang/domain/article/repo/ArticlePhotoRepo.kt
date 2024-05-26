package org.cyk.ktduitang.domain.article.repo

import jakarta.persistence.*
import org.cyk.ktduitang.infra.template.DatabaseTemplate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.stereotype.Component

@Table(name = "article_photo")
@EntityListeners(AuditingEntityListener::class)
class ArticlePhotoDo (
    @Id
    val id: Long, // 文章 id
    val aid: Long,
    val photoPath: String, // 图片路径
    val sort: Long, // 图片展示顺序(由小到大)
)

@Component
class ArticlePhotoRepo(
    val databaseTemplate: DatabaseTemplate<ArticleInfoDo, Long>
) {

}