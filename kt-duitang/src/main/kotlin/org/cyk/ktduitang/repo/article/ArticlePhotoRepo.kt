package org.cyk.ktduitang.repo.article

import jakarta.persistence.*
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.stereotype.Service

@Entity
@Table(name = "article_photo")
@EntityListeners(AuditingEntityListener::class)
class ArticlePhotoDo (
    @Id
    val id: Long,
    val photoPath: String,
    val sort: Long,
)

@Service
class ArticlePhotoRepo {
}