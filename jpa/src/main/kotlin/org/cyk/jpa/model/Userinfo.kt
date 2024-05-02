package org.cyk.jpa.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_info")
@EntityListeners(AuditingEntityListener::class)
data class Userinfo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val username: String,

    val age: Int,

    @CreatedDate
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "c_time")
    //注意: 除了 @EntityListeners(AuditingEntityListener::class), @EnableJpaAuditing，
    //Kotlin 这里需要使用 var，否则 @CreatedDate 不生效
    var cTime: Date? = null,

    @LastModifiedDate
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "u_time")
    //注意: 除了 @EntityListeners(AuditingEntityListener::class), @EnableJpaAuditing，
    //Kotlin 这里需要使用 var，否则 @LastModifiedDate 不生效
    var uTime: Date? = null,

)