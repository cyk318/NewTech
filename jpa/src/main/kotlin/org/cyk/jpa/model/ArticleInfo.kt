package org.cyk.jpa.model

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date


@Entity
@Table(name = "article_info")
@EntityListeners(AuditingEntityListener::class)
data class ArticleInfo (
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val userId: Long,

    val title: String,

    val content: String,

    @LastModifiedDate
    val cTime: Date? = null,
    val uTime: Date? = null,

)