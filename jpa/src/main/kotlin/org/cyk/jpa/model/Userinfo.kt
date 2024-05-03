package org.cyk.jpa.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Version

@Entity
@Table(name = "user_info")
@EntityListeners(AuditingEntityListener::class)
data class Userinfo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val username: String,

    val age: Int,

    //注意，关于审计信息，都需要使用 var 类型

    @Version
    var version: Long? = null,

    @CreatedBy
    @Column(name = "c_by")
    var cBy: String? = null,

    @LastModifiedBy
    @Column(name = "u_by")
    var uBy: String? = null,

    @CreatedDate
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "c_time")
    var cTime: Date? = null,

    @LastModifiedDate
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "u_time")
    var uTime: Date? = null,

)

@Configuration
class UserAuditor: AuditorAware<String> {

    /**
     * @return 获取当前创建或修改的用户
     */
    override fun getCurrentAuditor(): Optional<String> {
        return Optional.of("我是审计者 cyk")
    }

}