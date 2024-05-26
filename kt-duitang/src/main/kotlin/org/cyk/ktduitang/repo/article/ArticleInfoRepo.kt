package org.cyk.ktduitang.repo.article

import jakarta.persistence.*
import org.cyk.ktduitang.infra.template.DatabaseTemplate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.stereotype.Service
import java.util.*

@Entity
@Table(name = "article_info")
@EntityListeners(AuditingEntityListener::class)
data class ArticleInfoDo (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val userId: Long,
    val content: String,
    val state: String,
    @CreatedDate
    var cTime: Date? = null,
    @LastModifiedDate
    var uTime: Date? = null,
)

@Service
class ArticleInfoRepo(
    val databaseTemplate: DatabaseTemplate<ArticleInfoDo, Long>
) {



}